package clarifai2.api.request.input;

import clarifai2.Func1;
import clarifai2.api.BaseClarifaiClient;
import clarifai2.api.request.ClarifaiRequest;
import clarifai2.dto.input.ClarifaiInput;
import clarifai2.dto.input.ClarifaiInputUpdateAction;
import clarifai2.dto.prediction.Concept;
import clarifai2.internal.JSONArrayBuilder;
import clarifai2.internal.JSONObjectBuilder;
import clarifai2.internal.JSONUnmarshaler;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import okhttp3.Request;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class AddConceptsToInputRequest extends ClarifaiRequest.Builder<ClarifaiInput> {

  private final String inputID;

  private final List<Concept> concepts = new ArrayList<>();

  public AddConceptsToInputRequest(
      @NotNull final BaseClarifaiClient helper,
      @NotNull String inputID
  ) {
    super(helper);
    this.inputID = inputID;
  }

  @NotNull public final AddConceptsToInputRequest plus(@NotNull Concept... concepts) {
    return plus(Arrays.asList(concepts));
  }

  @NotNull public final AddConceptsToInputRequest plus(@NotNull List<Concept> concepts) {
    this.concepts.addAll(concepts);
    return this;
  }

  @NotNull @Override protected DeserializedRequest<ClarifaiInput> request() {
    return new DeserializedRequest<ClarifaiInput>() {
      @NotNull @Override public Request httpRequest() {
        final JsonObject body = new JSONObjectBuilder()
            .add("concepts", new JSONArrayBuilder()
                .addAll(concepts, new Func1<Concept, JsonElement>() {
                  @NotNull @Override public JsonElement call(@NotNull Concept concept) {
                    return client.gson.toJsonTree(concept);
                  }
                })
            )
            .add("action", client.gson.toJsonTree(ClarifaiInputUpdateAction.MERGE_CONCEPTS))
            .build();
        return patchRequest("/v2/inputs/" + inputID + "/data/concepts", body);
      }

      @NotNull @Override public JSONUnmarshaler<ClarifaiInput> unmarshaler() {
        return new JSONUnmarshaler<ClarifaiInput>() {
          @NotNull @Override public ClarifaiInput fromJSON(@NotNull Gson gson, @NotNull JsonElement json) {
            return gson.fromJson(json.getAsJsonObject().get("input"), ClarifaiInput.class);
          }
        };
      }
    };
  }
}
