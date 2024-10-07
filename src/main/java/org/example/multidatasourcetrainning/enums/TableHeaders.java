package org.example.multidatasourcetrainning.enums;

public enum TableHeaders {
    ID,
    COl1,
    COl2,
    COl3,
    DATE_CREATED;

    public static String[] getTableHeaders() {
        String[] headers = new String[TableHeaders.values().length];
        for (int i = 0; i < TableHeaders.values().length; i++) {
            headers[i] = TableHeaders.values()[i].name();
        }
        return headers;
    }
}
