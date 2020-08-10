package com.halukerd.db;

public enum DatasourceQueryResult {
    FOUND,
    FOUND_AND_UNKNOWN,
    FOUND_AND_KNOWN,
    NOT_FOUND,
    NOT_FOUND_AND_INSERTED,
    FOUND_COUNTED_STILL_UNKNOWN,
    FOUND_COUNTED_KNOWN,
    FAIL_ERROR_PANIC;

    DatasourceQueryResult() {
    }
}
