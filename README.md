Tropo Webapi Java
==============

Java library to interact with [Tropo's Webapi](https://www.tropo.com/docs/webapi/new_tropo_web_api_overview.htm).

Overview
--------

This is a very lightweight library that will help you to create applications that interact with Tropo from your Java based server. This library offers a type-safe object model that helps you to create requests to Tropo without having to deal with JSON at all. This way, you can minimize the errors that may happen due to invalid JSON being sent or parsed. 

Additionally, this library implements the session method from Tropo [Rest API](https://www.tropo.com/docs/rest/rest_api.htm) which means it can be used from Java web or stand-alone applications to execute remote scripts. For example you can integrate this library with a Java Swing application to do simple but powerful tasks like sending SMS, sending voice calls, transfering calls, etc. 

Requirements
------------

Current version is 0.1.

License
------------

[MIT License](https://github.com/tropo/tropo-webapi-java/blob/master/LICENSE)

Requirements
------------

You can install this library in any Java application or application server. The library depends primarily on Json-lib and Commons Httpclient. These libraries also depend on other artifacts. The following listing shows all the libraries that need to be present in an application that uses Tropo's Java Webapi:

	* commons-beanutils-1.8.1.jar
	* commons-collections-3.2.1.jar
	* commons-lang-2.5.jar
	* commons-logging-1.1.1.jar
	* commons-httpclient-3.1.jar
	* json-lib-2.4-jdk15.jar
	* ezmorph-1.0.6.jar

All libraries have been included in the [dist/dependencies](https://github.com/tropo/tropo-webapi-java/tree/master/dist/dependencies) folder.

Building
-------

Tropo Java Webapi is based in [Maven](http://maven.apache.org). Once that you have maven installed you can easily build this library by running the following command from the project's root folder:

    mvn package

Examples
-------

You can find loads of examples on how to use this library from the [unit tests folder|https://github.com/tropo/tropo-webapi-java/tree/master/src/test/java]. There is more than 100 different examples there. Here are a few ones:

    # Create a new PamFaxr object
	
	pamfaxr = PamFaxr.new :username => 'your_username', 
	                      :password => 'your_password'
	
	# Create a new FaxJob
	pamfaxr.create_fax_job
    
    # Add the cover sheet
	covers = pamfaxr.list_available_covers
	pamfaxr.set_cover(covers['Covers']['content'][1]['id'], 'Foobar is here!')
	
	# Add files
	pamfaxr.add_remote_file('https://s3.amazonaws.com/pamfax-test/Tropo.pdf')
	pamfaxr.add_file('examples/Tropo.pdf')
	
	# Add a recipient
	pamfaxr.add_recipient('+14155551212')
	
	# Loop until the fax is ready to send
	loop do
	  fax_state = pamfaxr.get_state
	  break if fax_state['FaxContainer']['state'] == 'ready_to_send'
	  sleep 5
	end
	
	# Send the fax
	pamfaxr.send_fax