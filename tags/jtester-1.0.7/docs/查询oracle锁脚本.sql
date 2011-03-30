SELECT s.username,
decode(l.type,'TM','TABLE LOCK','TX','ROW LOCK',NULL) LOCK_LEVEL,
o.owner,o.object_name,o.object_type,
s.sid,s.serial#,s.terminal,s.machine,s.program,s.osuser
FROM v$session s,v$lock l,dba_objects o
WHERE l.sid = s.sid
AND l.id1 = o.object_id(+)
AND s.username is NOT Null
and o.OWNER = 'APOLLO_TEST';