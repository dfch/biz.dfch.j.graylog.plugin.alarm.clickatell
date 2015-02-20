biz.dfch.j.graylog.plugin.alarm.dfchBizClickatellAlarmPlugin
============================================================

Plugin: biz.dfch.j.graylog.plugin.alarm.dfchBizClickatellAlarmPlugin

d-fens GmbH, General-Guisan-Strasse 6, CH-6300 Zug, Switzerland

This Graylog AlarmCallback Plugin lets you send custom formatted short messages (SMS) via the Clickatell Messaging Provider and works with the upcoming version 1 of Graylog.

See [Clickatell AlarmCallback Plugin for Graylog v1.0.0](http://d-fens.ch/2015/02/20/clickatell-alarmcallback-plugin-for-graylog-v1-0-0/) and [Creating a Graylog Output Plugin](http://d-fens.ch/2015/01/07/howto-creating-a-graylog-output-plugin/) (v0.92.x) for further description on how to create plugins.

For your information: your build will FAIL the tests unless you specify a valid [API key](https://github.com/dfch/biz.dfch.j.graylog.plugin.alarm.clickatell/blob/7c623213ea6c0f27d9516a3fc22c4cf6e7b65346/src/main/test/java/biz/dfch/j/clickatell/rest/ClickatellClientTest.java#L33).

You can [download the binary](https://drone.io/github.com/dfch/biz.dfch.j.graylog.plugin.alarm.clickatell/files) [![Build Status](https://drone.io/github.com/dfch/biz.dfch.j.graylog.plugin.alarm.clickatell/status.png)](https://drone.io/github.com/dfch/biz.dfch.j.graylog.plugin.alarm.clickatell/latest) at our [drone.io](https://drone.io/github.com/dfch) account.

Getting started for users
-------------------------

This project is using Maven and requires Java 7 or higher.

* Clone this repository.
* Run `mvn package` to build a JAR file.
* Optional: Run `mvn jdeb:jdeb` and `mvn rpm:rpm` to create a DEB and RPM package respectively.
* Copy generated jar file in target directory to your graylog server plugin directory.
* Restart the graylog server.
