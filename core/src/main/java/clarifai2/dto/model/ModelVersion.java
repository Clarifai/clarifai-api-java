package clarifai2.dto.model;

import clarifai2.dto.HasClarifaiIDRequired;
import clarifai2.internal.JSONAdapterFactory;
import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.reflect.TypeToken;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Date;

import static clarifai2.internal.InternalUtil.fromJson;

@SuppressWarnings("NullableProblems")
@AutoValue
@JsonAdapter(ModelVersion.Adapter.class)
public abstract class ModelVersion implements HasClarifaiIDRequired {

  @NotNull public abstract Date createdAt();
  @NotNull public abstract ModelTrainingStatus status();

  ModelVersion() {} // AutoValue instances only

  static class Adapter implements JsonDeserializer<ModelVersion> {
    @Override public ModelVersion deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
      final JsonObject root = json.getAsJsonObject();
      return new AutoValue_ModelVersion(
          root.get("id").getAsString(),
          fromJson(context, root.get("created_at"), Date.class),
          fromJson(context, root.get("status"), ModelTrainingStatus.class)
      );
    }
  }
}