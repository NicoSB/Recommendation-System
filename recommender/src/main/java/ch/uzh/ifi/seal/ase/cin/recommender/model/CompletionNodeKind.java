package ch.uzh.ifi.seal.ase.cin.recommender.model;

public enum CompletionNodeKind {
    CONSTANT,
    THIS_REFERENCE,
    FIELD_REFERENCE,
    TYPE_REFERENCE,
    PARAMETER_REFERENCE,
    RETURN_VALUE,
    NEWLY_INSTANTIATED_VALUE,
    NONE,
    DEFAULT
}
