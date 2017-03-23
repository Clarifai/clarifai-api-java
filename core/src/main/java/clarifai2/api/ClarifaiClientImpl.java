package clarifai2.api;

import clarifai2.api.request.concept.AddConceptsRequest;
import clarifai2.api.request.concept.GetConceptByIDRequest;
import clarifai2.api.request.concept.GetConceptsRequest;
import clarifai2.api.request.concept.SearchConceptsRequest;
import clarifai2.api.request.input.*;
import clarifai2.api.request.model.*;
import clarifai2.dto.model.DefaultModels;
import clarifai2.dto.prediction.Prediction;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

final class ClarifaiClientImpl extends BaseClarifaiClient implements ClarifaiClient {

  private final DefaultModels builtInModels = new DefaultModels(this);

  ClarifaiClientImpl(@NotNull ClarifaiBuilder builder) {
    super(builder);
  }

  @NotNull @Override public AddInputsRequest addInputs() {
    return new AddInputsRequest(this);
  }

  @NotNull @Override public PatchInputRequest mergeConceptsForInput(@NotNull String inputID) {
    return new PatchInputRequest(this, inputID, "merge");
  }

  @NotNull @Override public PatchInputRequest setConceptsForInput(@NotNull String inputID) {
    return new PatchInputRequest(this, inputID, "overwrite");
  }

  @NotNull @Override public PatchInputRequest removeConceptsForInput(@NotNull String inputID) {
    return new PatchInputRequest(this, inputID, "remove");
  }

  @NotNull @Override
  public PatchInputMetadataRequest addMetadataForInput(@NotNull String inputID, @NotNull JsonObject metadata) {
    return new PatchInputMetadataRequest(this, inputID, metadata);
  }

  @NotNull @Override public GetInputsRequest getInputs() {
    return new GetInputsRequest(this);
  }

  @NotNull @Override public GetInputRequest getInputByID(@NotNull final String inputID) {
    return new GetInputRequest(this, inputID);
  }

  @NotNull @Override public DeleteInputRequest deleteInput(@NotNull String inputID) {
    return new DeleteInputRequest(this, inputID);
  }

  @NotNull @Override public DeleteInputsBatchRequest deleteInputsBatch() {
    return new DeleteInputsBatchRequest(this);
  }

  @NotNull @Override
  public DeleteAllInputsRequest deleteAllInputs() {
    return new DeleteAllInputsRequest(this);
  }

  @NotNull @Override public GetInputsStatusRequest getInputsStatus() {
    return new GetInputsStatusRequest(this);
  }

  @NotNull @Override public SearchInputsRequest searchInputs(@NotNull SearchClause searchClause) {
    return searchInputs(Collections.singletonList(searchClause));
  }

  @NotNull @Override public SearchInputsRequest searchInputs(@NotNull SearchClause... searchClauses) {
    return searchInputs(Arrays.asList(searchClauses));
  }

  @NotNull @Override public SearchInputsRequest searchInputs(@NotNull List<SearchClause> searchClauses) {
    return new SearchInputsRequest(this, searchClauses);
  }

  @NotNull @Override public AddConceptsRequest addConcepts() {
    return new AddConceptsRequest(this);
  }

  @NotNull @Override public GetConceptsRequest getConcepts() {
    return new GetConceptsRequest(this);
  }

  @NotNull @Override public GetConceptByIDRequest getConceptByID(@NotNull final String conceptID) {
    return new GetConceptByIDRequest(this, conceptID);
  }

  @NotNull @Override
  public SearchConceptsRequest searchConcepts(@NotNull final String conceptSearchQuery) {
    return new SearchConceptsRequest(this, conceptSearchQuery);
  }

  @NotNull @Override public CreateModelRequest createModel(@NotNull final String id) {
    return new CreateModelRequest(this, id);
  }

  @NotNull @Override public DefaultModels getDefaultModels() {
    return builtInModels;
  }

  @NotNull @Override public GetModelsRequest getModels() {
    return new GetModelsRequest(this);
  }


  @NotNull @Override public GetModelRequest getModelByID(@NotNull String modelID) {
    return new GetModelRequest(this, modelID);
  }

  @NotNull @Override public DeleteModelRequest deleteModel(@NotNull final String modelID) {
    return new DeleteModelRequest(this, modelID);
  }

  @NotNull @Override public DeleteModelsBatchRequest deleteModelsBatch() {
    return new DeleteModelsBatchRequest(this);
  }

  @NotNull @Override
  public DeleteModelVersionRequest deleteModelVersion(@NotNull String modelID, @NotNull String versionID) {
    return new DeleteModelVersionRequest(this, modelID, versionID);
  }

  @NotNull @Override public DeleteAllModelsRequest deleteAllModels() {
    return new DeleteAllModelsRequest(this);
  }

  @NotNull @Override
  public GetModelVersionRequest getModelVersionByID(@NotNull String modelID, @NotNull String versionID) {
    return new GetModelVersionRequest(this, modelID, versionID);
  }

  @NotNull @Override
  public GetModelVersionsRequest getModelVersions(@NotNull final String modelID) {
    return new GetModelVersionsRequest(this, modelID);
  }

  @NotNull @Override public GetModelInputsRequest getModelInputs(@NotNull final String modelID) {
    return new GetModelInputsRequest(this, modelID);
  }

  @NotNull @Override public FindModelRequest findModel() {
    return new FindModelRequest(this);
  }

  @NotNull @Override public PatchModelRequest mergeConceptsForModel(@NotNull String modelID) {
    return new PatchModelRequest(this, modelID, Action.MERGE);
  }

  @NotNull @Override public PatchModelRequest setConceptsForModel(@NotNull String modelID) {
    return new PatchModelRequest(this, modelID, Action.OVERWRITE);
  }

  @NotNull @Override public PatchModelRequest removeConceptsForModel(@NotNull String modelID) {
    return new PatchModelRequest(this, modelID, Action.REMOVE);
  }

  @NotNull @Override public ModifyModelRequest modifyModel(@NotNull String modelID) {
    return new ModifyModelRequest(this, modelID);
  }

  @NotNull @Override public TrainModelRequest trainModel(@NotNull final String modelID) {
    return new TrainModelRequest(this, modelID);
  }

  @NotNull @Override public PredictRequest<Prediction> predict(@NotNull String modelID) {
    return new PredictRequest<>(this, modelID);
  }

}
