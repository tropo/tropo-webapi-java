Tropo Webapi Java <img src="http://hudson.voxeolabs.com/hudson/job/Tropo%202/lastSuccessfulBuild/buildStatus"/>
==============

Java library to interact with [Tropo's Webapi](https://www.tropo.com/docs/webapi/new_tropo_web_api_overview.htm).

Overview
--------

This is a very lightweight library that will help you to create applications that interact with Tropo from your Java based server. This library offers a type-safe object model that helps you to create requests to Tropo without having to deal with JSON at all. This way, you can minimize the errors that may happen due to invalid JSON being sent or parsed.

Additionally, this library implements the session method from Tropo [Rest API](https://www.tropo.com/docs/rest/rest_api.htm) which means it can be used from Java web or stand-alone applications to execute remote scripts. For example you can integrate this library with a Java Swing application to do simple but powerful tasks like sending SMS, sending voice calls, transfering calls, etc.

Requirements
------------

Current version is 15.13.0.

License
------------

[MIT License](https://github.com/tropo/tropo-webapi-java/blob/master/LICENSE)

Requirements
------------

You can install this library in any Java application or application server. The library depends primarily on Json-lib and Commons Httpclient. These libraries also depend on other artifacts. The following listing shows all the libraries that need to be present in an application that uses Tropo's Java Webapi:

	* gson-2.4.jar
	* httpclient-4.5.2.jar
	* httpcore-4.4.4.jar
	* commons-logging-1.2.jar
	* common-codec-1.9.jar
	

All libraries have been included in the [dist/dependencies](https://github.com/tropo/tropo-webapi-java/tree/master/dist/dependencies) folder.

Using the Maven artifact
------------------------

If you want to use the Maven artifact from your own projects you can add the following dependency to your project's pom.xml:

```xml
  <dependency>
    <groupId>com.voxeo.tropo</groupId>
    <artifactId>tropo-webapi-java</artifactId>
    <version>15.13.0</version>
  </dependency>
```

Building it from Source
------------------------

Tropo Java Webapi is based in [Maven](http://maven.apache.org). Once that you have maven installed you can easily build this library by running the following command from the project's root folder:

    mvn package

Examples
--------

You can find loads of examples on how to use this library from the [unit tests folder|https://github.com/tropo/tropo-webapi-java/tree/master/src/test/java]. There is more than 100 different examples there. Below you will find a few examples.

Say Hello and render text to the HTTP Servlet response object:

    HttpServletResponse response = ...
    Tropo tropo = new Tropo();
    tropo.say("1234");
    tropo.render(response)

Launch an application (can also be done from stand-alone apps) :

		String token = "bb308b34ed83d54cab226f4af7969e4c7d7d9196cdb3210b5ef0cb345616629005bfd05efe3f4409cd496ca2";
		Tropo tropo = new Tropo();
		TropoLaunchResult result = tropo.launchSession(token);

Launch an application and send some arguments (like for example passing an SMS number to a Tropo hosted file that sends SMS)

		String token = "bb308b34ed83d54cab226f4af7969e4c7d7d9196cdb3210b5ef0cb345616629005bfd05efe3f4409cd496ca2";
		Tropo tropo = new Tropo();
		Map params = new HashMap();
		params.put("number","623767896");
		TropoLaunchResult result = tropo.launchSession(token, params);

With Tropo's Java Webapi you can build Tropo apps with type-safety and traditional Java syntax:

		// Example 1
		Tropo tropo = new Tropo();
		RecordAction record = tropo.record("foo","http://sendme.com/tropo",true);
		record.transcription(ID("bling"), URL("mailto:jose@voxeo.com"), EMAIL_FORMAT("encoded"));
		record.say(VALUE("Please say your account number"));
		record.choices(VALUE("[5 DIGITS]"));

		// Example 2
		Tropo tropo = new Tropo();
		tropo.on(EVENT("error"), NEXT("/error.json"), SAY_OF_ON(new com.voxeo.tropo.actions.OnAction.Say("error"))); // For fatal programming errors. Log some details so we can fix it
		tropo.on(EVENT("hangup"), NEXT("/hangup.json"), SAY_OF_ON(new com.voxeo.tropo.actions.OnAction.Say("hangup"))); // When a user hangs or call is done. We will want to log some details.
		tropo.on(EVENT("continue"), NEXT("/next.json"), SAY_OF_ON(new com.voxeo.tropo.actions.OnAction.Say("continue")));
		tropo.say("Hello");
		tropo.startRecording(URL("http://heroku-voip.marksilver.net/post_audio_to_s3?filename=foo.wav&unique_id=bar"));
		// [From this point, until stop_recording(), we will record what the caller *and* the IVR say]
		tropo.say("You are now on the record.");
		// Prompt the user to incriminate themselve on-the-record
		tropo.say("Go ahead, sing-along.");
		tropo.say("http://denalidomain.com/music/keepers/HappyHappyBirthdaytoYou-Disney.mp3");

		// Example 3
		Tropo tropo = new Tropo();
		tropo.hangup();

At the same time, Tropo's Java Webapi defines a complete DSL to create applications in a much less verbosely manner. You can choose whatever syntax you are more comfortable with:

		// Example 1
		Tropo tropo = new Tropo();
		tropo
			.ask(Key.CHOICES("[5 DIGITS]"), Key.SAY_OF_ASK(new Say("Please say your account number")), Key.NAME("foo"),Key.BARGEIN(true),Key.TIMEOUT(30.0f),Key.REQUIRED(true));
		tropo.on(Key.EVENT("continue"), Key.NEXT("/result.json"));

		// Example 2
		Tropo tropo = new Tropo();
		tropo
			.conference(Key.ID("1234"),Key.NAME("foo"),Key.MUTE(false),Key.PLAY_TONES(false),Key.TERMINATOR("#"),Key.JOIN_PROMPT("Welcome to the conference"));

		// Example 3
		Tropo tropo = new Tropo();
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("fooKey", "fooValue");
		map.put("barKey", "barValue");
		tropo.call(Key.NAME("call"), Key.TO("foo"), Key.FROM("bar"), Key.NETWORK(Network.SMS), Key.CHANNEL(Channel.TEXT), Key.TIMEOUT(10.0f), Key.ANSWER_ON_MEDIA(false),
			Key.HEADERS(map));

		// Example 4
		Tropo tropo = new Tropo();
		tropo.message(Key.NAME("message"), Key.SAY_OF_MESSAGE(new Say("This is an announcement")),Key.TO("foo"), Key.FROM("bar"), Key.NETWORK(Network.SMS), Key.CHANNEL(Channel.TEXT),
			Key.TIMEOUT(10.0f), Key.ANSWER_ON_MEDIA(false));


Below you can also find a very trivial servlet POST method:

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Tropo tropo = new Tropo();
		tropo.say("Hello from Tropo. This is our first application.");
		tropo.render(response);
	}

And of course, you can read Tropo's session from the request and use it in your Java apps. Again, no Json knowledge required!

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Tropo tropo = new Tropo();
		TropoSession session = tropo.session(request);
		System.out.println("Call id: " + session.getCallId());
	}
