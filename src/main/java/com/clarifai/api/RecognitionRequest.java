package com.clarifai.api;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A request for recognition to be performed on one or more images. See
 * http://developer.clarifai.com for complete documentation on the Clarifai image recognition API.
 */
public class RecognitionRequest extends ClarifaiRequest {
  private static class Item {
    File file;
    byte[] imageBytes;
    String url;

    Item(File file) { this.file = file; }
    Item(byte[] imageBytes) { this.imageBytes = imageBytes; }
    Item(String url) { this.url = url; }

    InputStream getStream() throws IOException {
      if (file != null) {
        return new FileInputStream(file);
      } else if (imageBytes != null) {
        return new ByteArrayInputStream(imageBytes);
      }
      return null;
    }
  }

  private static Set<String> defaultOperations() {
    HashSet<String> operations = new HashSet<String>();
    operations.add("tag");
    return operations;
  }

  private final List<Item> items = new ArrayList<Item>();
  private String model = "default";
  private String language = "en";
  private final Set<String> operations = defaultOperations();
  private final Multipart multipart = new Multipart();

  /**
   * Constructs a new request for recognition on one or more images
   * @param files Files containing the image data to be recognized
   */
  public RecognitionRequest(File ... files) {
    for (File file : files) {
      items.add(new Item(file));
    }
  }

  /**
   * Constructs a new request for recognition on one or more images. The RecognitionRequest will
   * close the input streams when it is done sending them to the server.
   * @param imageByteArrays byte arrays containing image data to be recognized
   */
  public RecognitionRequest(byte[] ... imageByteArrays) {
    for (byte[] imageBytes : imageByteArrays) {
      items.add(new Item(imageBytes));
    }
  }

  /**
   * Constructs a new request for recognition on one or more images
   * @param urls publicly-accessible URLs for images to be recognized
   */
  public RecognitionRequest(String ... urls) {
    for (String url : urls) {
      items.add(new Item(url));
    }
  }

  /** Returns the name of the model to use for recognition. */
  public String getModel() {
    return model;
  }

  /** Sets the model to use for recognition. */
  public RecognitionRequest setModel(String model) {
    this.model = model;
    return this;
  }
  
  /** Sets the language to use for recognition. */
  public RecognitionRequest setLanguage(String language) {
    this.language = language;
    return this;
  }
  
  /** Returns the code of the language to use for recognition. */
  public String getLanguage() {
    return language;
  }

  /** Returns true (default) if tags should be included in the result, or false if not. */
  public boolean getIncludeTags() {
    return operations.contains("tag");
  }

  /** Sets whether to include tags in the result. */
  public RecognitionRequest setIncludeTags(boolean includeTags) {
    if (includeTags) {
      operations.add("tag");
    } else {
      operations.remove("tag");
    }
    return this;
  }

  /** Returns true if embeddings should be included in the result, or false (default) if not. */
  public boolean getIncludeEmbedding() {
    return operations.contains("embed");
  }

  /** Sets whether to include embeddings in the result. */
  public RecognitionRequest setIncludeEmbedding(boolean includeEmbedding) {
    if (includeEmbedding) {
      operations.add("embed");
    } else {
      operations.remove("embed");
    }
    return this;
  }

  /** Adds a custom operation to the list of operations the server should perform on the image. */
  public RecognitionRequest addCustomOperation(String customOperation) {
    operations.add(customOperation);
    return this;
  }

  @Override String getContentType() {
    return "multipart/form-data; boundary=" + multipart.getBoundary();
  }

  @Override void writeContent(OutputStream out) throws IOException {
    StringBuilder opParam = new StringBuilder();
    for (String op : operations) {
      if (opParam.length() > 0) {
        opParam.append(',');
      }
      opParam.append(op);
    }

    multipart.start(out);
    multipart.writeParameter("op", opParam.toString());
    multipart.writeParameter("model", model);
    multipart.writeParameter("language", language);
    
    for (Item item : items) {
      if (item.url != null) {
        multipart.writeParameter("url", item.url);
      } else {
        InputStream in = item.getStream();
        try {
          multipart.writeMedia("encoded_data", "media", in);
        } finally {
          in.close();
        }
      }
    }
    multipart.finish();
  }
}
