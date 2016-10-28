package clarifai2.api.request.model;

import clarifai2.api.BaseClarifaiClient;
import clarifai2.api.request.ClarifaiRequest;
import clarifai2.dto.model.ModelVersion;
import clarifai2.internal.JSONUnmarshaler;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import okhttp3.Request;
import org.jetbrains.annotations.NotNull;

public final class GetModelVersionRequest extends ClarifaiRequest.Builder<ModelVersion> {

  @NotNull private final String modelID;
  @NotNull private final String versionID;

  public GetModelVersionRequest(
      @NotNull BaseClarifaiClient client,
      @NotNull String modelID,
      @NotNull String versionID
  ) {
    super(client);
    this.modelID = modelID;
    this.versionID = versionID;
  }

  @NotNull @Override protected DeserializedRequest<ModelVersion> request() {
    return new DeserializedRequest<ModelVersion>() {
      @NotNull @Override public Request httpRequest() {
        return getRequest(String.format("/v2/models/%s/versions/%s", modelID, versionID));
      }

      @NotNull @Override public JSONUnmarshaler<ModelVersion> unmarshaler() {
        return new JSONUnmarshaler<ModelVersion>() {
          @NotNull @Override public ModelVersion fromJSON(@NotNull Gson gson, @NotNull JsonElement json) {
            return gson.fromJson(json, ModelVersion.class);
          }
        };
      }
    };
  }
}
