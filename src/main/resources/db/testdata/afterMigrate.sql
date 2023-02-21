-- a small test database for SignUp with some users, groups and events

-- don't use use_id = 0, it's the admin user used for bootstrapping the system
INSERT INTO public.users VALUES (-1, 'Fredrik', 'Unknown', 'En glad statsminister', 'fredrik.unknown@crisp.se', '', 'NormalUser', '*', 'Gravatar', NULL, NULL, NULL) ON CONFLICT DO NOTHING;
INSERT INTO public.users VALUES (-2, 'Torbjörn', 'Fälldin', '', 'torbjörn.fälldin@crisp.se', '', 'NormalUser', '*', 'Gravatar', NULL, NULL, NULL) ON CONFLICT DO NOTHING;
INSERT INTO public.users VALUES (-3, 'Göran', 'Persson', 'En f.d. statsminister', 'göran.persson@crisp.se', '', 'NormalUser', '*', 'Gravatar', NULL, NULL, NULL) ON CONFLICT DO NOTHING;
INSERT INTO public.users VALUES (-4, 'Frodo', 'Baggins', 'Ringbärare', 'frodo.baggins@crisp.se', '', 'NormalUser', '*', 'Gravatar', NULL, NULL, NULL) ON CONFLICT DO NOTHING;
INSERT INTO public.users VALUES (-5, 'John', 'Doe', 'Okändis', 'john@doe.net', '', 'NormalUser', '*', 'Gravatar', NULL, NULL, NULL) ON CONFLICT DO NOTHING;
INSERT INTO public.users VALUES (-9, 'TestFName1', 'TestLName1', 'Test Comment 1', 'miguel.h.ogren@gmail.com', '', 'NormalUser', '*', 'Gravatar', NULL, NULL, NULL) ON CONFLICT DO NOTHING;
INSERT INTO public.users VALUES (-10, 'TestFName2', 'TestLName2', 'Test Comment 2', 'jacobjohanssn2001@gmail.com', '', 'NormalUser', '*', 'Gravatar', NULL, NULL, NULL) ON CONFLICT DO NOTHING;
INSERT INTO public.users VALUES (-69, 'UserOnlyFor', 'UnitTests', 'Använd inte denna användare', 'unitTest@testing.com', '', 'NormalUser', '*', 'Gravatar', NULL, NULL, NULL) ON CONFLICT DO NOTHING;


INSERT INTO public.groups VALUES (-1, 'Crisp Rocket Days', 'För dej som vill lära dej mer', '', 'Crisp Rocket Days') ON CONFLICT DO NOTHING;
INSERT INTO public.groups VALUES (-2, 'Näsknäckarna', 'Innebandylaget för hårdingar', '', 'Näsknäckarna') ON CONFLICT DO NOTHING;
INSERT INTO public.groups VALUES (-9, 'TestName', 'Test description', '', 'Test_Mail_Subject_Prefix') ON CONFLICT DO NOTHING;
INSERT INTO public.groups VALUES (-59, 'GroupOnlyForUnitTest', 'Gör inget med denna grupp tack', 'Testers') ON CONFLICT DO NOTHING;


INSERT INTO public.memberships VALUES (-1, -1, -1) ON CONFLICT DO NOTHING;
INSERT INTO public.memberships VALUES (-2, -2, -1) ON CONFLICT DO NOTHING;
INSERT INTO public.memberships VALUES (-3, -3, -2) ON CONFLICT DO NOTHING;
INSERT INTO public.memberships VALUES (-4, -4, -2) ON CONFLICT DO NOTHING;
INSERT INTO public.memberships VALUES (-9, -9, -9) ON CONFLICT DO NOTHING;
INSERT INTO public.memberships VALUES (-10, -10, -9) ON CONFLICT DO NOTHING;
INSERT INTO public.memberships VALUES (-64, -69, -59) ON CONFLICT DO NOTHING;


INSERT INTO public.events VALUES (-1, 'Crisp RD', 'Vad jag lärde mig av BigFamilyTrip', 'Crisp Office', '2021-05-03 18:00:00', '2021-05-03 19:00:00', -1, '2021-05-02 00:00:00', false, 'Created', NULL, NULL) ON CONFLICT DO NOTHING;
INSERT INTO public.events VALUES (-2, 'Crisp RD', 'Scala 3.0 och Play 3.0', 'Crisp Office', '2013-05-03 18:00:00', '2013-05-03 18:00:00', -1, '2013-05-03 00:00:00', false, 'Created', NULL, NULL) ON CONFLICT DO NOTHING;
INSERT INTO public.events VALUES (-3, 'Julbord', 'Hej tomtegubbar...', 'Crisp Office', '2012-12-20 17:30:00', '2012-12-20 17:30:00', -2, '2012-12-20 00:00:00', false, 'Created', NULL, NULL) ON CONFLICT DO NOTHING;
INSERT INTO public.events VALUES (-9, 'Test Name', 'Test Description', 'Test Venue', '2022-12-12 17:00:00', '2022-12-12 17:30:00', -9, '2022-12-20 00:00:00', false, 'Created', NULL, NULL) ON CONFLICT DO NOTHING;
INSERT INTO public.events VALUES (-66, 'UnitTestEvent1', 'Only used for Unit Testing', 'Unit Venue', '2022-11-11 11:00:00', '2022-11-11 11:11:00', -59, '2022-11-20 00:00:00', false, 'Created', NULL, NULL) ON CONFLICT DO NOTHING;
INSERT INTO public.events VALUES (-67, 'EventUnitTest2', 'Used Unit for Testing Only', 'Venue testing Unit', '2030-09-09 09:00:00', '2030-09-09 09:09:00', -59, '2030-09-19 00:00:00', false, 'Created', NULL, NULL) ON CONFLICT DO NOTHING;


INSERT INTO public.participations VALUES (-1, 'Off', 'Måste till svärmor', -3, -1, 1, '2021-05-03 10:00:00') ON CONFLICT DO NOTHING;
INSERT INTO public.participations VALUES (-2, 'On', 'Trevligt!', -1, -1, 1, '2021-04-02 21:15:00') ON CONFLICT DO NOTHING;
INSERT INTO public.participations VALUES (-3, 'Maybe', 'Får se...', -4, -1, 1, '2021-05-01 13:00:00') ON CONFLICT DO NOTHING;
INSERT INTO public.participations VALUES (-4, 'On', 'Nice!', -5, -1, 1, '2021-05-02 00:00:00') ON CONFLICT DO NOTHING;
INSERT INTO public.participations VALUES (-9, 'Maybe', 'Nice!', -10, -9, 1, '2022-05-02 00:00:00') ON CONFLICT DO NOTHING;


INSERT INTO public.reminders VALUES (-1, -9, '2022-11-15 01:00:00') ON CONFLICT DO NOTHING;
INSERT INTO public.reminders VALUES (-2, -9, '2022-11-17 01:00:00') ON CONFLICT DO NOTHING;
INSERT INTO public.reminders VALUES (-3, -9, '2022-11-16 01:00:00') ON CONFLICT DO NOTHING;


UPDATE public.users SET email='fredrik.unknown@mailinator.com' WHERE id=-1;
UPDATE public.users SET email='torbjorn.falldin@mailinator.com' WHERE id=-2;
UPDATE public.users SET email='goran.persson@mailinator.com' WHERE id=-3;
UPDATE public.users SET email='frodo.baggins@mailinator.com' WHERE id=-4;
UPDATE public.users SET email='john.doe@mailinator.com' WHERE id=-5;
UPDATE public.users SET email='miguel.h.ogren@mailinator.com' WHERE id=-9;
UPDATE public.users SET email='jacobjohanssn2001@mailinator.com' WHERE id=-10;
