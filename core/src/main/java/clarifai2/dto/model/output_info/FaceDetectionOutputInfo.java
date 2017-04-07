package clarifai2.dto.model.output_info;

import clarifai2.internal.JSONAdapterFactory;
import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.reflect.TypeToken;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static clarifai2.internal.InternalUtil.assertJsonIs;

@SuppressWarnings("NullableProblems")
@AutoValue
@JsonAdapter(FaceDetectionOutputInfo.Adapter.class)
public abstract class FaceDetectionOutputInfo extends OutputInfo {

  @NotNull public abstract String type();
  @NotNull public abstract String typeExt();

  FaceDetectionOutputInfo() {} // AutoValue instances only

  static class Adapter extends JSONAdapterFactory<FaceDetectionOutputInfo> {
    @Nullable @Override protected Deserializer<FaceDetectionOutputInfo> deserializer() {
      return new Deserializer<FaceDetectionOutputInfo>() {
        @Nullable @Override
        public FaceDetectionOutputInfo deserialize(
            @NotNull JsonElement json,
            @NotNull TypeToken<FaceDetectionOutputInfo> type,
            @NotNull Gson gson
        ) {
          final JsonObject root = assertJsonIs(json, JsonObject.class);
          String modelType = root.getAsJsonPrimitive("type").getAsString();
          String typeExt = root.getAsJsonPrimitive("type_ext").getAsString();
          return new AutoValue_FaceDetectionOutputInfo(modelType, typeExt);
        }
      };
    }

    @NotNull @Override protected TypeToken<FaceDetectionOutputInfo> typeToken() {
      return new TypeToken<FaceDetectionOutputInfo>() {};
    }
  }
}