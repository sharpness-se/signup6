-- a small test database for SignUp with some users, groups and events

INSERT INTO public.users VALUES (-1, 'Fredrik', 'Unknown', 'En glad statsminister', 'fredrik.unknown@crisp.se', '', 'NormalUser', '*', 'Gravatar', NULL, NULL, NULL) ON CONFLICT DO NOTHING;
INSERT INTO public.users VALUES (-2, 'Torbjörn', 'Fälldin', '', 'torbjörn.fälldin@crisp.se', '', 'NormalUser', '*', 'Gravatar', NULL, NULL, NULL) ON CONFLICT DO NOTHING;
INSERT INTO public.users VALUES (-3, 'Göran', 'Persson', 'En f.d. statsminister', 'göran.persson@crisp.se', '', 'NormalUser', '*', 'Gravatar', NULL, NULL, NULL) ON CONFLICT DO NOTHING;
INSERT INTO public.users VALUES (-4, 'Frodo', 'Baggins', 'Ringbärare', 'frodo.baggins@crisp.se', '', 'NormalUser', '*', 'Gravatar', NULL, NULL, NULL) ON CONFLICT DO NOTHING;
INSERT INTO public.users VALUES (-5, 'John', 'Doe', 'Okändis', 'john@doe.net', '', 'NormalUser', '*', 'Gravatar', NULL, NULL, NULL) ON CONFLICT DO NOTHING;
-- don't use use_id = -1


INSERT INTO public.groups VALUES (-1, 'Crisp Rocket Days', 'För dej som vill lära dej mer', '', 'Crisp Rocket Days');
INSERT INTO public.groups VALUES (-2, 'Näsknäckarna', 'Innebandylaget för hårdingar', '', 'Näsknäckarna');


INSERT INTO public.memberships VALUES (-1, -1, -1);
INSERT INTO public.memberships VALUES (-2, -2, -1);
INSERT INTO public.memberships VALUES (-3, -3, -2);
INSERT INTO public.memberships VALUES (-4, -4, -2);


INSERT INTO public.events VALUES (-1, 'Crisp RD', 'Vad jag lärde mig av BigFamilyTrip', 'Crisp Office', '2012-05-03 18:00:00', '2012-05-03 18:00:00', -1, '2012-05-03 00:00:00', false, 'Created', NULL, NULL);
INSERT INTO public.events VALUES (-2, 'Crisp RD', 'Scala 3.0 och Play 3.0', 'Crisp Office', '2013-05-03 18:00:00', '2013-05-03 18:00:00', -1, '2013-05-03 00:00:00', false, 'Created', NULL, NULL);
INSERT INTO public.events VALUES (-3, 'Julbord', 'Hej tomtegubbar...', 'Crisp Office', '2012-12-20 17:30:00', '2012-12-20 17:30:00', -2, '2012-12-20 00:00:00', false, 'Created', NULL, NULL);


INSERT INTO public.participations VALUES (1, 'Off', 'Måste till svärmor', -3, -1, 1, NULL);
INSERT INTO public.participations VALUES (2, 'On', 'Trevligt!', -1, -1, 1, NULL);
INSERT INTO public.participations VALUES (3, 'Maybe', 'Får se...', -4, -1, 1, NULL);
INSERT INTO public.participations VALUES (-5, 'On', 'Nice!', -5, -1, 1, NULL);

