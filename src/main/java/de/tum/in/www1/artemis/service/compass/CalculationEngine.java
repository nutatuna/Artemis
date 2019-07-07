package de.tum.in.www1.artemis.service.compass;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import com.google.gson.JsonObject;

import de.tum.in.www1.artemis.domain.Feedback;
import de.tum.in.www1.artemis.domain.Result;
import de.tum.in.www1.artemis.service.compass.grade.Grade;

public interface CalculationEngine {

    /**
     * Get the id of the next optimal modeling submission. Optimal means that an assessment for this model results in the biggest knowledge gain for Compass which can be used for
     * automatic assessments.
     *
     * @return the id of the next optimal modeling submission
     */
    Long getNextOptimalModel();

    /**
     * Get the assessment result for a model. If no assessment is saved for the given model, it tries to create a new one automatically with the existing information of the engine.
     *
     * @param modelId the id of the model
     * @return the assessment result for the model
     */
    Grade getGradeForModel(long modelId);

    Collection<Long> getModelIds();

    /**
     * Update the engine with a new manual assessment.
     *
     * @param modelingAssessment the new assessment as list of individual Feedback objects
     * @param submissionId       the id of the corresponding model
     */
    void notifyNewAssessment(List<Feedback> modelingAssessment, long submissionId);

    /**
     * Add a new model
     *
     * @param model   the new model as raw sting
     * @param modelId the id of the new model
     */
    void notifyNewModel(String model, long modelId);

    /**
     * @return the time when the engine has been used last
     */
    LocalDateTime getLastUsedAt();

    /**
     * Get the list of model IDs which have been selected for the next manual assessments. Typically these models are the ones where Compass learns the most, when they are
     * assessed. All returned models do not have a complete assessment.
     *
     * @return a list of modelIds that should be assessed next
     */
    List<Long> getModelsWaitingForAssessment();

    /**
     * Removes the model with the given id from the list of models that should be assessed next. The isAssessed flag indicates if the corresponding model still needs an assessment
     * or not, i.e. if the flag is true, the model will no longer be considered for assessment by Compass.
     *
     * @param modelId    the id of the model
     * @param isAssessed a flag indicating if the model still needs an assessment or not
     */
    void removeModelWaitingForAssessment(long modelId, boolean isAssessed);

    /**
     * Mark a model as unassessed, i.e. that it (still) needs to be assessed. By that it is not locked anymore and can be returned for assessment by Compass again.
     *
     * @param modelId the id of the model that should be marked as unassessed
     */
    void markModelAsUnassessed(long modelId);

    /**
     * Generate a Feedback list from the given Grade for the given model. The Grade was generated by Compass earlier in the automatic assessment process. It basically contains the
     * Compass internal representation of the automatic assessment for the given model.
     *
     * @param grade   the Grade generated by Compass from which the Feedback list should be generated from
     * @param modelId the id of the corresponding model
     * @param result  the corresponding result that will be linked to the newly created feedback items
     * @return the list of Feedback items generated from the Grade
     */
    List<Feedback> convertToFeedback(Grade grade, long modelId, Result result);

    /**
     * @return statistics about the UML model
     */
    JsonObject getStatistics();

    /**
     * Get the confidence for a specific model element with the given id. The confidence is the percentage indicating how certain Compass is about the assessment of the given model
     * element. If it is smaller than a configured threshold, the element with the given id will not be assessed automatically.
     *
     * @param elementId    the id of the model element
     * @param submissionId the id of the submission that contains the corresponding model
     * @return the confidence for the model element with the given id
     */
    double getConfidenceForElement(String elementId, long submissionId);

}
