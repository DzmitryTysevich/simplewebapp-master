CREATE TABLE EMPLOYEE
(
    ID            INTEGER PRIMARY KEY,
    FIRST_NAME    VARCHAR(10),
    LAST_NAME     VARCHAR(10),
    DEPARTMENT_ID INTEGER,
    JOB_TITLE     VARCHAR(10),
    GENDER        VARCHAR(10),
    DATE_OF_BIRTH DATE
);

COMMIT;