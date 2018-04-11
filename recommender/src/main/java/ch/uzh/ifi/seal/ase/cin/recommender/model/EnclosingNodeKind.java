package ch.uzh.ifi.seal.ase.cin.recommender.model;

public enum EnclosingNodeKind {
    ARGUMENT_EXPRESSION,
    WHILE,
    FOR,
    BRANCHING_CONDITION,
    LOCAL_VARIABLE_RVALUE,
    FIELD_DECLARATION_RVALUE,
    ASSIGNMENT_RVALUE,
    ALLOCATION_EXPRESSION,
    ARRAY_ALLOCATION,
    DEFAULT
}
