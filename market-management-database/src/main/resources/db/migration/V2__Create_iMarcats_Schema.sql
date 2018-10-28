    create table ACTIVATION_AUDIT (
        ID bigint not null auto_increment,
        DATE_TIME datetime not null,
        USER_ID varchar(50),
        primary key (ID)
    );

    create table APPROVAL_AUDIT (
        ID bigint not null auto_increment,
        DATE_TIME datetime not null,
        USER_ID varchar(50),
        primary key (ID)
    );

    create table ASK (
        ID bigint not null auto_increment,
        DATE_OF_QUOTE datetime,
        QUOTE double precision not null,
        VALID_QUOTE boolean not null,
        SIZE integer not null,
        primary key (ID)
    );

    create table ASSET_CLASS (
        NAME varchar(150) not null,
        DESCRIPTION varchar(250) not null,
        LAST_UPDATE_TIMESTAMP datetime not null,
        PARENT_NAME varchar(150),
        VERSION bigint,
        PROPERTY_HOLDER_ID bigint,
        primary key (NAME)
    );

    create table AUDIT_TRAIL_ENTRY (
        ID bigint not null auto_increment,
        AUDIT_ENTRY_ACTION integer,
        DATE_TIME datetime not null,
        OBJECT_TYPE varchar(50),
        RELATED_INFORMATION varchar(350),
        USER_ID varchar(50),
        primary key (ID)
    );

    create table BID (
        ID bigint not null auto_increment,
        DATE_OF_QUOTE datetime,
        QUOTE double precision not null,
        VALID_QUOTE boolean not null,
        SIZE integer not null,
        primary key (ID)
    );

    create table BOOK_ENTRY_ORDER_IDS (
        BookEntryBase_ID bigint not null,
        ORDER_IDS bigint
    );

    create table BOOLEAN_PROPERTY (
        ID bigint not null auto_increment,
        NAME varchar(50) not null,
        VALUE boolean not null,
        PROPERTY_HOLDER_ID bigint not null,
        primary key (ID)
    );

    create table BUSINESS_CALENDAR_DAY (
        ID bigint not null auto_increment,
        DATE_STRING varchar(10) not null,
        DAY varchar(255) not null,
        primary key (ID)
    );

    create table BUY_BOOK (
        ID bigint not null auto_increment,
        VERSION bigint,
        primary key (ID)
    );

    create table BUY_BOOK_BUY_ORDER_BOOK_ENTRY (
        BUY_BOOK_ID bigint not null,
        _orderBookEntries_ID bigint not null,
        unique (_orderBookEntries_ID)
    );

    create table BUY_ORDER_BOOK_ENTRY (
        ID bigint not null,
        LAST_UPDATE_TIMESTAMP datetime not null,
        DATE_OF_QUOTE datetime,
        QUOTE double precision not null,
        VALID_QUOTE boolean not null,
        ORDER_TYPE varchar(255),
        primary key (ID)
    );

    create table CHANGE_AUDIT (
        ID bigint not null auto_increment,
        DATE_TIME datetime not null,
        USER_ID varchar(50),
        primary key (ID)
    );

    create table CIRCUIT_BREAKER (
        ID bigint not null auto_increment,
        MAXIMUM_QUOTE_IMPROVEMENT double precision,
        ORDER_REJECT_ACTION varchar(255),
        primary key (ID)
    );

    create table CIRCUIT_BREAKER_HALT_RULE (
        CIRCUIT_BREAKER_ID bigint not null,
        _haltRules_ID bigint not null,
        unique (_haltRules_ID)
    );

    create table CLOSING_QUOTE (
        ID bigint not null auto_increment,
        DATE_OF_QUOTE datetime,
        QUOTE double precision not null,
        VALID_QUOTE boolean not null,
        primary key (ID)
    );

    create table CORPORATE_INFORMATION (
        ID bigint not null auto_increment,
        CITY varchar(50) not null,
        COUNTRY varchar(50) not null,
        POSTAL_CODE varchar(50),
        STATE varchar(2),
        STREET varchar(50) not null,
        NAME varchar(150) not null,
        WEB_SITE varchar(150),
        primary key (ID)
    );

    create table CREATION_AUDIT (
        ID bigint not null auto_increment,
        DATE_TIME datetime not null,
        USER_ID varchar(50),
        primary key (ID)
    );

    create table DATE_PROPERTY (
        ID bigint not null auto_increment,
        NAME varchar(50) not null,
        VALUE datetime not null,
        PROPERTY_HOLDER_ID bigint not null,
        primary key (ID)
    );

    create table DATE_RANGE_PROPERTY (
        ID bigint not null auto_increment,
        NAME varchar(50) not null,
        OUTSIDE_THE_RANGE boolean,
        END_DATE date not null,
        START_DATE date not null,
        PROPERTY_HOLDER_ID bigint not null,
        primary key (ID)
    );

    create table DEACTIVATION_AUDIT (
        ID bigint not null auto_increment,
        DATE_TIME datetime not null,
        USER_ID varchar(50),
        primary key (ID)
    );

    create table DELETION_AUDIT (
        ID bigint not null auto_increment,
        DATE_TIME datetime not null,
        USER_ID varchar(50),
        primary key (ID)
    );

    create table DOUBLE_PROPERTY (
        ID bigint not null auto_increment,
        NAME varchar(50) not null,
        UNIT varchar(50),
        VALUE double precision not null,
        PROPERTY_HOLDER_ID bigint not null,
        primary key (ID)
    );

    create table END_TIME (
        ID bigint not null auto_increment,
        HOUR integer not null,
        MINUTE integer not null,
        SECOND integer not null,
        TIMEZONE varchar(50) not null,
        primary key (ID)
    );

    create table EXPIRATION_PROPERTIES (
        ID bigint not null auto_increment,
        PROPERTY_HOLDER_ID bigint,
        primary key (ID)
    );

    create table HALT_RULE (
        ID bigint not null auto_increment,
        CHANGE_TYPE varchar(255) not null,
        HALT_PERIOD integer not null,
        QUOTE_CHANGE_AMOUNT double precision not null,
        primary key (ID)
    );

    create table INSTRUMENT (
        INSTRUMENT_CODE varchar(150) not null,
        ACTIVATION_DATE date,
        ACTIVATION_STATUS varchar(255) not null,
        ASSET_CLASS_NAME varchar(150),
        CONTRACT_SIZE double precision not null,
        CONTRACT_SIZE_UNIT varchar(50) not null,
        CITY varchar(50) not null,
        COUNTRY varchar(50) not null,
        POSTAL_CODE varchar(50),
        STATE varchar(2),
        STREET varchar(50) not null,
        DELIVERY_PERIOD varchar(255) not null,
        DENOMINATION_CURRENCY varchar(3) not null,
        DESCRIPTION varchar(250) not null,
        INSTRUMENT_ROLLED_OVER_FROM varchar(150),
        ISIN varchar(12),
        LAST_UPDATE_TIMESTAMP datetime not null,
        MASTER_AGREEMENT_DOCUMENT varchar(150),
        NAME varchar(150) not null,
        QUOTE_TYPE varchar(255) not null,
        RECORD_PURCHASE_AS_POSITION varchar(255) not null,
        ROLLABLE boolean not null,
        SETTLEMENT_PRICE varchar(255),
        SETTLEMENT_TYPE varchar(255) not null,
        SUB_TYPE varchar(50),
        TYPE varchar(255) not null,
        UNDERLYING_CODE varchar(150) not null,
        UNDERLYING_TYPE varchar(255) not null,
        VERSION bigint,
        APPROVAL_AUDIT_ID bigint,
        CHANGE_AUDIT_ID bigint,
        CREATION_AUDIT_ID bigint,
        PROPERTY_HOLDER_ID bigint,
        ROLLOVER_AUDIT_ID bigint,
        SUSPENSION_AUDIT_ID bigint,
        primary key (INSTRUMENT_CODE)
    );

    create table INSTRUMENT_ROLLABLE_PROPERTY_NAMES (
        Instrument_INSTRUMENT_CODE varchar(150) not null,
        ROLLABLE_PROPERTY_NAMES varchar(255)
    );

    create table INT_PROPERTY (
        ID bigint not null auto_increment,
        NAME varchar(50) not null,
        UNIT varchar(50),
        VALUE bigint not null,
        PROPERTY_HOLDER_ID bigint not null,
        primary key (ID)
    );

    create table LAST_TRADE (
        ID bigint not null auto_increment,
        DATE_OF_QUOTE datetime,
        QUOTE double precision not null,
        VALID_QUOTE boolean not null,
        SIZE integer not null,
        primary key (ID)
    );

    create table LIMIT_QUOTE (
        ID bigint not null auto_increment,
        DATE_OF_QUOTE datetime,
        QUOTE double precision not null,
        VALID_QUOTE boolean not null,
        primary key (ID)
    );

    create table MARKET (
        MARKET_CODE varchar(150) not null unique,
        ACTIVATION_STATUS varchar(255) not null,
        ALLOW_HIDDEN_ORDERS boolean not null,
        ALLOW_SIZE_RESTRICTION_ON_ORDERS boolean not null,
        BUSINESS_ENTITY_CODE varchar(150) not null,
        CALL_MARKET_MAINTENANCE_ACTION_KEY bigint,
        CLEARING_BANK varchar(150) not null,
        COMMISSION double precision not null,
        COMMISSION_CURRENCY varchar(3) not null,
        DESCRIPTION varchar(250) not null,
        EXECUTION_SYSTEM varchar(255) not null,
        HALT_LEVEL integer,
        INSTRUMENT_CODE varchar(150) not null,
        LAST_UPDATE_TIMESTAMP datetime not null,
        MARKET_CALL_ACTION_KEY bigint,
        MARKET_CLOSE_ACTION_KEY bigint,
        MARKET_MAINTENANCE_ACTION_KEY bigint,
        MARKET_OPEN_ACTION_KEY bigint,
        MARKET_OPERATION_CONTRACT varchar(150),
        MARKET_OPERATION_DAYS varchar(255),
        MARKET_OPERATOR_CODE varchar(250) not null,
        MARKET_REOPEN_ACTION_KEY bigint,
        MARKET_TIMEZONE varchar(50) not null,
        MAXIMUM_CONTRACTS_TRADED integer not null,
        MIMIMUM_CONTRACTS_TRADED integer not null,
        MINIMUM_QUOTE_INCREMENT double precision not null,
        NAME varchar(150) not null,
        NEXT_MARKET_CALL_DATE datetime,
        QUOTE_TYPE varchar(255) not null,
        STATE varchar(255) not null,
        TRADING_DAY_END_HOUR integer,
        TRADING_DAY_END_MINUTE integer,
        TRADING_DAY_END_SECOND integer,
        TRADING_DAY_END_TIMEZONE varchar(255),
        TRADING_SESSION varchar(255) not null,
        VERSION bigint,
        ACTIVATION_AUDIT_ID bigint,
        APPROVAL_AUDIT_ID bigint,
        BUY_BOOK_ID bigint,
        CHANGE_AUDIT_ID bigint,
        CIRCUIT_BREAKER_ID bigint,
        CLOSING_QUOTE_ID bigint,
        CREATION_AUDIT_ID bigint,
        DEACTIVATION_AUDIT_ID bigint,
        LAST_TRADE_ID bigint,
        OPENING_QUOTE_ID bigint,
        PREVIOUS_BEST_ASK_ID bigint,
        PREVIOUS_BEST_BID_ID bigint,
        PREVIOUS_CLOSING_QUOTE_ID bigint,
        PREVIOUS_LAST_TRADE_ID bigint,
        PREVIOUS_OPENING_QUOTE_ID bigint,
        ROLLOVER_AUDIT_ID bigint,
        SELL_BOOK_ID bigint,
        SUSPENSION_AUDIT_ID bigint,
        END_TIME_ID bigint,
        START_TIME_ID bigint,
        primary key (MARKET_CODE)
    );

    create table MARKET_BUSINESS_CALENDAR_DAY (
        MARKET_MARKET_CODE varchar(150) not null,
        _businessCalendarDays_ID bigint not null,
        unique (_businessCalendarDays_ID)
    );

    create table MARKET_OPERATOR (
        CODE varchar(150) not null,
        ACTIVATION_STATUS varchar(255) not null,
        BUSINESS_ENTITY_CODE varchar(150) not null,
        DESCRIPTION varchar(250) not null,
        LAST_UPDATE_TIMESTAMP datetime not null,
        MARKET_OPERTOR_AGREEMENT varchar(150),
        NAME varchar(150) not null,
        OWNER_USER_ID varchar(50) not null,
        VERSION bigint,
        APPROVAL_AUDIT_ID bigint,
        CHANGE_AUDIT_ID bigint,
        CREATION_AUDIT_ID bigint,
        SUSPENSION_AUDIT_ID bigint,
        primary key (CODE)
    );

    create table MARKET_SECONDARY_ORDER_PRECEDENCE_RULES (
        Market_MARKET_CODE varchar(150) not null,
        SECONDARY_ORDER_PRECEDENCE_RULES varchar(255)
    );

    create table MATCHED_TRADE (
        TRANSACTION_ID bigint not null auto_increment,
        MARKET_OF_THE_TRADE varchar(150) not null,
        MATCHED_SIZE integer not null,
        SETTLEMENT_STATE varchar(255) not null,
        STANDING_ORDER_SIDE varchar(255),
        DATE_OF_QUOTE datetime,
        QUOTE double precision not null,
        VALID_QUOTE boolean not null,
        BUY_SIDE_ID bigint,
        SELL_SIDE_ID bigint,
        primary key (TRANSACTION_ID)
    );

    create table OBJECT_PROPERTY (
        ID bigint not null auto_increment,
        NAME varchar(50) not null,
        VALUE longblob not null,
        primary key (ID)
    );

    create table OPENING_QUOTE (
        ID bigint not null auto_increment,
        DATE_OF_QUOTE datetime,
        QUOTE double precision not null,
        VALID_QUOTE boolean not null,
        primary key (ID)
    );

    create table ORDER_ENTITY (
        ID bigint not null auto_increment,
        CANCELLATION_COMMENT_LANGUAGE_KEY varchar(255),
        COMMISSION_CHARGED boolean,
        DISPLAY_ORDER boolean,
        EXECUTE_ENTRIRE_ORDER_AT_ONCE boolean,
        EXECUTED_SIZE integer,
        EXPIRATION_INSTRUCTION varchar(255) not null,
        EXPIRATION_TRIGGER_ACTION_KEY bigint,
        EXTERNAL_ORDER_REFERENCE varchar(36) not null,
        LAST_UPDATE_TIMESTAMP datetime not null,
        MINIMUM_SIZE_OF_EXECUTION integer,
        QUOTE_CHANGE_TRIGGER_KEY bigint,
        SIDE varchar(255) not null,
        SIZE integer not null,
        STATE varchar(255) not null,
        SUBMISSION_DATE datetime,
        SUBMITTER_ID varchar(50),
        TARGET_ACCOUNT_ID bigint,
        TARGET_MARKET_CODE varchar(150),
        TRIGGER_INSTRUCTION integer not null,
        TYPE varchar(255) not null,
        VERSION bigint,
        CREATION_AUDIT_ID bigint,
        CURRENT_STOP_QUOTE_ID bigint,
        EXPIRATION_PROPERTIES_ID bigint,
        LIMIT_QUOTE_VALUE_ID bigint,
        ORDER_PROPERTIES_ID bigint,
        TRIGGER_PROPERTIES_ID bigint,
        primary key (ID),
        unique (EXTERNAL_ORDER_REFERENCE, SUBMITTER_ID, TARGET_MARKET_CODE)
    );

    create table ORDER_PROPERTIES (
        ID bigint not null auto_increment,
        PROPERTY_HOLDER_ID bigint,
        primary key (ID)
    );

    create table ORDER_QUOTE (
        ID bigint not null auto_increment,
        DATE_OF_QUOTE datetime,
        QUOTE double precision not null,
        VALID_QUOTE boolean not null,
        primary key (ID)
    );

    create table PREVIOUS_CLOSING_QUOTE (
        ID bigint not null auto_increment,
        DATE_OF_QUOTE datetime,
        QUOTE double precision not null,
        VALID_QUOTE boolean not null,
        primary key (ID)
    );

    create table PREVIOUS_LAST_QUOTE (
        ID bigint not null auto_increment,
        DATE_OF_QUOTE datetime,
        QUOTE double precision not null,
        VALID_QUOTE boolean not null,
        SIZE integer not null,
        primary key (ID)
    );

    create table PREVIOUS_OPENING_QUOTE (
        ID bigint not null auto_increment,
        DATE_OF_QUOTE datetime,
        QUOTE double precision not null,
        VALID_QUOTE boolean not null,
        primary key (ID)
    );

    create table PRODUCT (
        PRODUCT_CODE varchar(150) not null,
        ACTIVATION_DATE date,
        ACTIVATION_STATUS integer not null,
        CATEGORY varchar(50),
        DESCRIPTION varchar(250) not null,
        LAST_UPDATE_TIMESTAMP datetime not null,
        NAME varchar(150) not null,
        PRODUCT_CODE_ROLLED_OVER_FROM varchar(150),
        PRODUCT_DEFINITION_DOCUMENT varchar(150),
        ROLLABLE boolean not null,
        SUB_CATEGORY varchar(50),
        PRODUCT_TYPE varchar(255) not null,
        VERSION bigint,
        APPROVAL_AUDIT_ID bigint,
        CHANGE_AUDIT_ID bigint,
        CREATION_AUDIT_ID bigint,
        PROPERTY_HOLDER_ID bigint,
        ROLLOVER_AUDIT_ID bigint,
        SUSPENSION_AUDIT_ID bigint,
        primary key (PRODUCT_CODE)
    );

    create table PRODUCT_ROLLABLE_PROPERTY_NAMES (
        Product_PRODUCT_CODE varchar(150) not null,
        ROLLABLE_PROPERTY_NAME varchar(255)
    );

    create table PROPERTY_HOLDER (
        ID bigint not null auto_increment,
        primary key (ID)
    );

    create table ROLLOVER_AUDIT (
        ID bigint not null auto_increment,
        DATE_TIME datetime not null,
        USER_ID varchar(50),
        primary key (ID)
    );

    create table SELL_BOOK (
        ID bigint not null auto_increment,
        VERSION bigint,
        primary key (ID)
    );

    create table SELL_BOOK_SELL_ORDER_BOOK_ENTRY (
        SELL_BOOK_ID bigint not null,
        _orderBookEntries_ID bigint not null,
        unique (_orderBookEntries_ID)
    );

    create table SELL_ORDER_BOOK_ENTRY (
        ID bigint not null,
        LAST_UPDATE_TIMESTAMP datetime not null,
        DATE_OF_QUOTE datetime,
        QUOTE double precision not null,
        VALID_QUOTE boolean not null,
        ORDER_TYPE varchar(255),
        primary key (ID)
    );

    create table START_TIME (
        ID bigint not null auto_increment,
        HOUR integer not null,
        MINUTE integer not null,
        SECOND integer not null,
        TIMEZONE varchar(50) not null,
        primary key (ID)
    );

    create table STOP_QUOTE (
        ID bigint not null auto_increment,
        DATE_OF_QUOTE datetime,
        QUOTE double precision not null,
        VALID_QUOTE boolean not null,
        primary key (ID)
    );

    create table STRING_LIST_PROPERTY (
        ID bigint not null auto_increment,
        NAME varchar(50) not null,
        PROPERTY_HOLDER_ID bigint not null,
        primary key (ID)
    );

    create table STRING_LIST_PROPERTY_VALUES (
        StringListProperty_ID bigint not null,
        VALUE varchar(255) not null
    );

    create table STRING_PROPERTY (
        ID bigint not null auto_increment,
        NAME varchar(50) not null,
        VALUE varchar(150) not null,
        PROPERTY_HOLDER_ID bigint not null,
        primary key (ID)
    );

    create table SUSPENSION_AUDIT (
        ID bigint not null auto_increment,
        DATE_TIME datetime not null,
        USER_ID varchar(50),
        primary key (ID)
    );

    create table TIME_PROPERTY (
        ID bigint not null auto_increment,
        NAME varchar(50) not null,
        HOUR integer not null,
        MINUTE integer not null,
        SECOND integer not null,
        TIMEZONE varchar(50) not null,
        PROPERTY_HOLDER_ID bigint not null,
        primary key (ID)
    );

    create table TIME_RANGE_PROPERTY (
        ID bigint not null auto_increment,
        NAME varchar(50) not null,
        OUTSIDE_THE_RANGE boolean,
        END_TIME_ID bigint,
        START_TIME_ID bigint,
        PROPERTY_HOLDER_ID bigint not null,
        primary key (ID)
    );

    create table TRADE_PROPERTIES (
        ID bigint not null auto_increment,
        PROPERTY_HOLDER_ID bigint,
        primary key (ID)
    );

    create table TRADE_QUOTE (
        ID bigint not null auto_increment,
        DATE_OF_QUOTE datetime,
        QUOTE double precision not null,
        VALID_QUOTE boolean not null,
        primary key (ID)
    );

    create table TRADE_SIDE (
        ID bigint not null auto_increment,
        COMMISSION double precision not null,
        COMMISSION_CURRENCY varchar(3) not null,
        CONTRACT_SIDE double precision not null,
        EXTERNAL_ORDER_REFERENCE varchar(36) not null,
        INSTRUMENT_OF_THE_TRADE varchar(150) not null,
        MARKET_OF_THE_TRADE varchar(150) not null,
        MATCHED_SIZE integer not null,
        ORDER_TYPE varchar(255) not null,
        SETTLEMENT_STATE varchar(255) not null,
        SIDE varchar(255) not null,
        TRADE_DATE_TIME datetime not null,
        TRADER_ID varchar(50) not null,
        _matchedTrade_TRANSACTION_ID bigint,
        ORDER_QUOTE_ID bigint,
        TRADE_PROPERTIES_ID bigint,
        TRADE_QUOTE_ID bigint,
        primary key (ID)
    );

    create table TRIGGER_PROPERTIES (
        ID bigint not null auto_increment,
        PROPERTY_HOLDER_ID bigint,
        primary key (ID)
    );

    create table UNIT_PROPERTY (
        ID bigint not null auto_increment,
        NAME varchar(50) not null,
        UNIT varchar(50) not null,
        PROPERTY_HOLDER_ID bigint not null,
        primary key (ID)
    );

    alter table ASSET_CLASS 
        add index FK153DAB29F9955AAE (PROPERTY_HOLDER_ID), 
        add constraint FK153DAB29F9955AAE 
        foreign key (PROPERTY_HOLDER_ID) 
        references PROPERTY_HOLDER (ID);

    alter table BOOLEAN_PROPERTY 
        add index FKACD4902CF9955AAE (PROPERTY_HOLDER_ID), 
        add constraint FKACD4902CF9955AAE 
        foreign key (PROPERTY_HOLDER_ID) 
        references PROPERTY_HOLDER (ID);

    alter table BUY_BOOK_BUY_ORDER_BOOK_ENTRY 
        add index FKA865F4E3AD26D173 (BUY_BOOK_ID), 
        add constraint FKA865F4E3AD26D173 
        foreign key (BUY_BOOK_ID) 
        references BUY_BOOK (ID);

    alter table BUY_BOOK_BUY_ORDER_BOOK_ENTRY 
        add index FKA865F4E3121F80CF (_orderBookEntries_ID), 
        add constraint FKA865F4E3121F80CF 
        foreign key (_orderBookEntries_ID) 
        references BUY_ORDER_BOOK_ENTRY (ID);

    alter table CIRCUIT_BREAKER_HALT_RULE 
        add index FK5E2FDE91B1D23D3B (CIRCUIT_BREAKER_ID), 
        add constraint FK5E2FDE91B1D23D3B 
        foreign key (CIRCUIT_BREAKER_ID) 
        references CIRCUIT_BREAKER (ID);

    alter table CIRCUIT_BREAKER_HALT_RULE 
        add index FK5E2FDE91B85E0574 (_haltRules_ID), 
        add constraint FK5E2FDE91B85E0574 
        foreign key (_haltRules_ID) 
        references HALT_RULE (ID);

    alter table DATE_PROPERTY 
        add index FKFA97DB46F9955AAE (PROPERTY_HOLDER_ID), 
        add constraint FKFA97DB46F9955AAE 
        foreign key (PROPERTY_HOLDER_ID) 
        references PROPERTY_HOLDER (ID);

    alter table DATE_RANGE_PROPERTY 
        add index FK83482368F9955AAE (PROPERTY_HOLDER_ID), 
        add constraint FK83482368F9955AAE 
        foreign key (PROPERTY_HOLDER_ID) 
        references PROPERTY_HOLDER (ID);

    alter table DOUBLE_PROPERTY 
        add index FKC390CA83F9955AAE (PROPERTY_HOLDER_ID), 
        add constraint FKC390CA83F9955AAE 
        foreign key (PROPERTY_HOLDER_ID) 
        references PROPERTY_HOLDER (ID);

    alter table EXPIRATION_PROPERTIES 
        add index FK7188E4C3F9955AAE (PROPERTY_HOLDER_ID), 
        add constraint FK7188E4C3F9955AAE 
        foreign key (PROPERTY_HOLDER_ID) 
        references PROPERTY_HOLDER (ID);

    alter table INSTRUMENT 
        add index FK70174FE71FBECD25 (CHANGE_AUDIT_ID), 
        add constraint FK70174FE71FBECD25 
        foreign key (CHANGE_AUDIT_ID) 
        references CHANGE_AUDIT (ID);

    alter table INSTRUMENT 
        add index FK70174FE76ADBD63F (APPROVAL_AUDIT_ID), 
        add constraint FK70174FE76ADBD63F 
        foreign key (APPROVAL_AUDIT_ID) 
        references APPROVAL_AUDIT (ID);

    alter table INSTRUMENT 
        add index FK70174FE7DEC91DA3 (ROLLOVER_AUDIT_ID), 
        add constraint FK70174FE7DEC91DA3 
        foreign key (ROLLOVER_AUDIT_ID) 
        references ROLLOVER_AUDIT (ID);

    alter table INSTRUMENT 
        add index FK70174FE7F9955AAE (PROPERTY_HOLDER_ID), 
        add constraint FK70174FE7F9955AAE 
        foreign key (PROPERTY_HOLDER_ID) 
        references PROPERTY_HOLDER (ID);

    alter table INSTRUMENT 
        add index FK70174FE7887D358B (SUSPENSION_AUDIT_ID), 
        add constraint FK70174FE7887D358B 
        foreign key (SUSPENSION_AUDIT_ID) 
        references SUSPENSION_AUDIT (ID);

    alter table INSTRUMENT 
        add index FK70174FE71966B607 (CREATION_AUDIT_ID), 
        add constraint FK70174FE71966B607 
        foreign key (CREATION_AUDIT_ID) 
        references CREATION_AUDIT (ID);

    alter table INSTRUMENT_ROLLABLE_PROPERTY_NAMES 
        add index FK2D65E1CE13038DE8 (Instrument_INSTRUMENT_CODE), 
        add constraint FK2D65E1CE13038DE8 
        foreign key (Instrument_INSTRUMENT_CODE) 
        references INSTRUMENT (INSTRUMENT_CODE);

    alter table INT_PROPERTY 
        add index FKB048BC45F9955AAE (PROPERTY_HOLDER_ID), 
        add constraint FKB048BC45F9955AAE 
        foreign key (PROPERTY_HOLDER_ID) 
        references PROPERTY_HOLDER (ID);

    alter table MARKET 
        add index FK871F883C5A8AE184 (PREVIOUS_BEST_BID_ID), 
        add constraint FK871F883C5A8AE184 
        foreign key (PREVIOUS_BEST_BID_ID) 
        references BID (ID);

    alter table MARKET 
        add index FK871F883C6ADBD63F (APPROVAL_AUDIT_ID), 
        add constraint FK871F883C6ADBD63F 
        foreign key (APPROVAL_AUDIT_ID) 
        references APPROVAL_AUDIT (ID);

    alter table MARKET 
        add index FK871F883C8D1EE338 (CLOSING_QUOTE_ID), 
        add constraint FK871F883C8D1EE338 
        foreign key (CLOSING_QUOTE_ID) 
        references CLOSING_QUOTE (ID);

    alter table MARKET 
        add index FK871F883CB1D23D3B (CIRCUIT_BREAKER_ID), 
        add constraint FK871F883CB1D23D3B 
        foreign key (CIRCUIT_BREAKER_ID) 
        references CIRCUIT_BREAKER (ID);

    alter table MARKET 
        add index FK871F883C32131D76 (START_TIME_ID), 
        add constraint FK871F883C32131D76 
        foreign key (START_TIME_ID) 
        references START_TIME (ID);

    alter table MARKET 
        add index FK871F883CE878E3B3 (SELL_BOOK_ID), 
        add constraint FK871F883CE878E3B3 
        foreign key (SELL_BOOK_ID) 
        references SELL_BOOK (ID);

    alter table MARKET 
        add index FK871F883C2C3B5AA8 (END_TIME_ID), 
        add constraint FK871F883C2C3B5AA8 
        foreign key (END_TIME_ID) 
        references END_TIME (ID);

    alter table MARKET 
        add index FK871F883C1FBECD25 (CHANGE_AUDIT_ID), 
        add constraint FK871F883C1FBECD25 
        foreign key (CHANGE_AUDIT_ID) 
        references CHANGE_AUDIT (ID);

    alter table MARKET 
        add index FK871F883C691A7ED9 (ACTIVATION_AUDIT_ID), 
        add constraint FK871F883C691A7ED9 
        foreign key (ACTIVATION_AUDIT_ID) 
        references ACTIVATION_AUDIT (ID);

    alter table MARKET 
        add index FK871F883CDEC91DA3 (ROLLOVER_AUDIT_ID), 
        add constraint FK871F883CDEC91DA3 
        foreign key (ROLLOVER_AUDIT_ID) 
        references ROLLOVER_AUDIT (ID);

    alter table MARKET 
        add index FK871F883C4D3E8F17 (DEACTIVATION_AUDIT_ID), 
        add constraint FK871F883C4D3E8F17 
        foreign key (DEACTIVATION_AUDIT_ID) 
        references DEACTIVATION_AUDIT (ID);

    alter table MARKET 
        add index FK871F883C887D358B (SUSPENSION_AUDIT_ID), 
        add constraint FK871F883C887D358B 
        foreign key (SUSPENSION_AUDIT_ID) 
        references SUSPENSION_AUDIT (ID);

    alter table MARKET 
        add index FK871F883C364CD711 (PREVIOUS_OPENING_QUOTE_ID), 
        add constraint FK871F883C364CD711 
        foreign key (PREVIOUS_OPENING_QUOTE_ID) 
        references PREVIOUS_OPENING_QUOTE (ID);

    alter table MARKET 
        add index FK871F883CAD26D173 (BUY_BOOK_ID), 
        add constraint FK871F883CAD26D173 
        foreign key (BUY_BOOK_ID) 
        references BUY_BOOK (ID);

    alter table MARKET 
        add index FK871F883C1966B607 (CREATION_AUDIT_ID), 
        add constraint FK871F883C1966B607 
        foreign key (CREATION_AUDIT_ID) 
        references CREATION_AUDIT (ID);

    alter table MARKET 
        add index FK871F883C59662004 (PREVIOUS_BEST_ASK_ID), 
        add constraint FK871F883C59662004 
        foreign key (PREVIOUS_BEST_ASK_ID) 
        references ASK (ID);

    alter table MARKET 
        add index FK871F883C552EB404 (LAST_TRADE_ID), 
        add constraint FK871F883C552EB404 
        foreign key (LAST_TRADE_ID) 
        references LAST_TRADE (ID);

    alter table MARKET 
        add index FK871F883CC37BE885 (PREVIOUS_LAST_TRADE_ID), 
        add constraint FK871F883CC37BE885 
        foreign key (PREVIOUS_LAST_TRADE_ID) 
        references PREVIOUS_LAST_QUOTE (ID);

    alter table MARKET 
        add index FK871F883C4BDBE732 (OPENING_QUOTE_ID), 
        add constraint FK871F883C4BDBE732 
        foreign key (OPENING_QUOTE_ID) 
        references OPENING_QUOTE (ID);

    alter table MARKET 
        add index FK871F883C778FD317 (PREVIOUS_CLOSING_QUOTE_ID), 
        add constraint FK871F883C778FD317 
        foreign key (PREVIOUS_CLOSING_QUOTE_ID) 
        references PREVIOUS_CLOSING_QUOTE (ID);

    alter table MARKET_BUSINESS_CALENDAR_DAY 
        add index FK1777595719F291DD (MARKET_MARKET_CODE), 
        add constraint FK1777595719F291DD 
        foreign key (MARKET_MARKET_CODE) 
        references MARKET (MARKET_CODE);

    alter table MARKET_BUSINESS_CALENDAR_DAY 
        add index FK177759579788CF1B (_businessCalendarDays_ID), 
        add constraint FK177759579788CF1B 
        foreign key (_businessCalendarDays_ID) 
        references BUSINESS_CALENDAR_DAY (ID);

    alter table MARKET_OPERATOR 
        add index FK674545471FBECD25 (CHANGE_AUDIT_ID), 
        add constraint FK674545471FBECD25 
        foreign key (CHANGE_AUDIT_ID) 
        references CHANGE_AUDIT (ID);

    alter table MARKET_OPERATOR 
        add index FK674545476ADBD63F (APPROVAL_AUDIT_ID), 
        add constraint FK674545476ADBD63F 
        foreign key (APPROVAL_AUDIT_ID) 
        references APPROVAL_AUDIT (ID);

    alter table MARKET_OPERATOR 
        add index FK67454547887D358B (SUSPENSION_AUDIT_ID), 
        add constraint FK67454547887D358B 
        foreign key (SUSPENSION_AUDIT_ID) 
        references SUSPENSION_AUDIT (ID);

    alter table MARKET_OPERATOR 
        add index FK674545471966B607 (CREATION_AUDIT_ID), 
        add constraint FK674545471966B607 
        foreign key (CREATION_AUDIT_ID) 
        references CREATION_AUDIT (ID);

    alter table MARKET_SECONDARY_ORDER_PRECEDENCE_RULES 
        add index FKFC69DA4119F291DD (Market_MARKET_CODE), 
        add constraint FKFC69DA4119F291DD 
        foreign key (Market_MARKET_CODE) 
        references MARKET (MARKET_CODE);

    alter table MATCHED_TRADE 
        add index FKE2C19E49E7ABBB91 (BUY_SIDE_ID), 
        add constraint FKE2C19E49E7ABBB91 
        foreign key (BUY_SIDE_ID) 
        references TRADE_SIDE (ID);

    alter table MATCHED_TRADE 
        add index FKE2C19E49FD18805D (SELL_SIDE_ID), 
        add constraint FKE2C19E49FD18805D 
        foreign key (SELL_SIDE_ID) 
        references TRADE_SIDE (ID);

    alter table ORDER_ENTITY 
        add index FK4CC87614250D25A2 (TRIGGER_PROPERTIES_ID), 
        add constraint FK4CC87614250D25A2 
        foreign key (TRIGGER_PROPERTIES_ID) 
        references TRIGGER_PROPERTIES (ID);

    alter table ORDER_ENTITY 
        add index FK4CC87614D46F76E6 (CURRENT_STOP_QUOTE_ID), 
        add constraint FK4CC87614D46F76E6 
        foreign key (CURRENT_STOP_QUOTE_ID) 
        references STOP_QUOTE (ID);

    alter table ORDER_ENTITY 
        add index FK4CC876143508E88E (ORDER_PROPERTIES_ID), 
        add constraint FK4CC876143508E88E 
        foreign key (ORDER_PROPERTIES_ID) 
        references ORDER_PROPERTIES (ID);

    alter table ORDER_ENTITY 
        add index FK4CC876146DFBE3A (LIMIT_QUOTE_VALUE_ID), 
        add constraint FK4CC876146DFBE3A 
        foreign key (LIMIT_QUOTE_VALUE_ID) 
        references LIMIT_QUOTE (ID);

    alter table ORDER_ENTITY 
        add index FK4CC876141966B607 (CREATION_AUDIT_ID), 
        add constraint FK4CC876141966B607 
        foreign key (CREATION_AUDIT_ID) 
        references CREATION_AUDIT (ID);

    alter table ORDER_ENTITY 
        add index FK4CC87614649244C2 (EXPIRATION_PROPERTIES_ID), 
        add constraint FK4CC87614649244C2 
        foreign key (EXPIRATION_PROPERTIES_ID) 
        references EXPIRATION_PROPERTIES (ID);

    alter table ORDER_PROPERTIES 
        add index FK4A0296E4F9955AAE (PROPERTY_HOLDER_ID), 
        add constraint FK4A0296E4F9955AAE 
        foreign key (PROPERTY_HOLDER_ID) 
        references PROPERTY_HOLDER (ID);

    alter table PRODUCT 
        add index FK185958CF1FBECD25 (CHANGE_AUDIT_ID), 
        add constraint FK185958CF1FBECD25 
        foreign key (CHANGE_AUDIT_ID) 
        references CHANGE_AUDIT (ID);

    alter table PRODUCT 
        add index FK185958CF6ADBD63F (APPROVAL_AUDIT_ID), 
        add constraint FK185958CF6ADBD63F 
        foreign key (APPROVAL_AUDIT_ID) 
        references APPROVAL_AUDIT (ID);

    alter table PRODUCT 
        add index FK185958CFDEC91DA3 (ROLLOVER_AUDIT_ID), 
        add constraint FK185958CFDEC91DA3 
        foreign key (ROLLOVER_AUDIT_ID) 
        references ROLLOVER_AUDIT (ID);

    alter table PRODUCT 
        add index FK185958CFF9955AAE (PROPERTY_HOLDER_ID), 
        add constraint FK185958CFF9955AAE 
        foreign key (PROPERTY_HOLDER_ID) 
        references PROPERTY_HOLDER (ID);

    alter table PRODUCT 
        add index FK185958CF887D358B (SUSPENSION_AUDIT_ID), 
        add constraint FK185958CF887D358B 
        foreign key (SUSPENSION_AUDIT_ID) 
        references SUSPENSION_AUDIT (ID);

    alter table PRODUCT 
        add index FK185958CF1966B607 (CREATION_AUDIT_ID), 
        add constraint FK185958CF1966B607 
        foreign key (CREATION_AUDIT_ID) 
        references CREATION_AUDIT (ID);

    alter table PRODUCT_ROLLABLE_PROPERTY_NAMES 
        add index FKFDD5B2B6DB9FB2E8 (Product_PRODUCT_CODE), 
        add constraint FKFDD5B2B6DB9FB2E8 
        foreign key (Product_PRODUCT_CODE) 
        references PRODUCT (PRODUCT_CODE);

    alter table SELL_BOOK_SELL_ORDER_BOOK_ENTRY 
        add index FKDFEAF6B1E878E3B3 (SELL_BOOK_ID), 
        add constraint FKDFEAF6B1E878E3B3 
        foreign key (SELL_BOOK_ID) 
        references SELL_BOOK (ID);

    alter table SELL_BOOK_SELL_ORDER_BOOK_ENTRY 
        add index FKDFEAF6B11F298D43 (_orderBookEntries_ID), 
        add constraint FKDFEAF6B11F298D43 
        foreign key (_orderBookEntries_ID) 
        references SELL_ORDER_BOOK_ENTRY (ID);

    alter table STRING_LIST_PROPERTY 
        add index FK6BCB5CA8F9955AAE (PROPERTY_HOLDER_ID), 
        add constraint FK6BCB5CA8F9955AAE 
        foreign key (PROPERTY_HOLDER_ID) 
        references PROPERTY_HOLDER (ID);

    alter table STRING_LIST_PROPERTY_VALUES 
        add index FK19A3249949578F23 (StringListProperty_ID), 
        add constraint FK19A3249949578F23 
        foreign key (StringListProperty_ID) 
        references STRING_LIST_PROPERTY (ID);

    alter table STRING_PROPERTY 
        add index FKED6FAE43F9955AAE (PROPERTY_HOLDER_ID), 
        add constraint FKED6FAE43F9955AAE 
        foreign key (PROPERTY_HOLDER_ID) 
        references PROPERTY_HOLDER (ID);

    alter table TIME_PROPERTY 
        add index FKE148B07F9955AAE (PROPERTY_HOLDER_ID), 
        add constraint FKE148B07F9955AAE 
        foreign key (PROPERTY_HOLDER_ID) 
        references PROPERTY_HOLDER (ID);

    alter table TIME_RANGE_PROPERTY 
        add index FK4DD83E69F9955AAE (PROPERTY_HOLDER_ID), 
        add constraint FK4DD83E69F9955AAE 
        foreign key (PROPERTY_HOLDER_ID) 
        references PROPERTY_HOLDER (ID);

    alter table TIME_RANGE_PROPERTY 
        add index FK4DD83E6932131D76 (START_TIME_ID), 
        add constraint FK4DD83E6932131D76 
        foreign key (START_TIME_ID) 
        references START_TIME (ID);

    alter table TIME_RANGE_PROPERTY 
        add index FK4DD83E692C3B5AA8 (END_TIME_ID), 
        add constraint FK4DD83E692C3B5AA8 
        foreign key (END_TIME_ID) 
        references END_TIME (ID);

    alter table TRADE_PROPERTIES 
        add index FKB9BA230EF9955AAE (PROPERTY_HOLDER_ID), 
        add constraint FKB9BA230EF9955AAE 
        foreign key (PROPERTY_HOLDER_ID) 
        references PROPERTY_HOLDER (ID);

    alter table TRADE_SIDE 
        add index FK8E78BF7231627A70 (_matchedTrade_TRANSACTION_ID), 
        add constraint FK8E78BF7231627A70 
        foreign key (_matchedTrade_TRANSACTION_ID) 
        references MATCHED_TRADE (TRANSACTION_ID);

    alter table TRADE_SIDE 
        add index FK8E78BF72341A3ABA (TRADE_PROPERTIES_ID), 
        add constraint FK8E78BF72341A3ABA 
        foreign key (TRADE_PROPERTIES_ID) 
        references TRADE_PROPERTIES (ID);

    alter table TRADE_SIDE 
        add index FK8E78BF721CD4A806 (ORDER_QUOTE_ID), 
        add constraint FK8E78BF721CD4A806 
        foreign key (ORDER_QUOTE_ID) 
        references ORDER_QUOTE (ID);

    alter table TRADE_SIDE 
        add index FK8E78BF72B52BAA5A (TRADE_QUOTE_ID), 
        add constraint FK8E78BF72B52BAA5A 
        foreign key (TRADE_QUOTE_ID) 
        references TRADE_QUOTE (ID);

    alter table TRIGGER_PROPERTIES 
        add index FK200A7E3AF9955AAE (PROPERTY_HOLDER_ID), 
        add constraint FK200A7E3AF9955AAE 
        foreign key (PROPERTY_HOLDER_ID) 
        references PROPERTY_HOLDER (ID);

    alter table UNIT_PROPERTY 
        add index FK1FCAA230F9955AAE (PROPERTY_HOLDER_ID), 
        add constraint FK1FCAA230F9955AAE 
        foreign key (PROPERTY_HOLDER_ID) 
        references PROPERTY_HOLDER (ID);

    create table hibernate_sequences (
         sequence_name varchar(255),
         sequence_next_hi_value integer 
    ) ;
