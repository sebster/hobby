package com.sebster.repository.api;

import lombok.Value;

@Value
public class SimpleTestObject implements TestObject {

	int id;
	String a;
	String b;

}
