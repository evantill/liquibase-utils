--liquibase formatted sql
--preconditions onFail:HALT onError:HALT
--precondition-context-defined

 SELECT CURRENT_DATE AS today, CURRENT_TIME AS now FROM (VALUES(0))