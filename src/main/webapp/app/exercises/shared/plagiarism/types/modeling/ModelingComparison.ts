import { PlagiarismComparison } from '../PlagiarismComparison';
import { ModelingPlagiarismSubmission } from 'app/exercises/shared/plagiarism/types/modeling/ModelingPlagiarismSubmission';

/**
 * Pair of two similar modeling exercise submissions.
 */
export class ModelingComparison extends PlagiarismComparison {
    submissionA: ModelingPlagiarismSubmission;
    submissionB: ModelingPlagiarismSubmission;
}