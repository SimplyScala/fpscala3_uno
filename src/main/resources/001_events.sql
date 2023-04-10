drop table events;
create table events
(
    valuez        jsonb not null,
    insertorder   bigserial not null,
    aggregatename text not null,
    aggregateuid  text not null,
    processuid    text not null,
    --eventname     text not null generated always as ( (valuez -> 'event' ->> 'eventName')::text ) stored,
    sentdate      text not null
    --streamid      text not null generated always as ( ((valuez ->> 'aggregatename')::text || '-' || (valuez -> 'event' ->> 'aggregateUid')::text)) stored
);

/*alter table events
    add primary key (aggregatename, aggregateuid, processuid);*/
create unique index on events USING btree (insertorder);
create index on events (aggregatename, aggregateuid);
--create index on events (streamid);drop table events;
