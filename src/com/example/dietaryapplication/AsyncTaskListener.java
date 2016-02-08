package com.example.dietaryapplication;

public interface AsyncTaskListener {
	void asyncTaskSuccessful(String data);	
	void asyncTaskFailed(String data);
}
