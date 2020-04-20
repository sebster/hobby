package com.sebster.remarkable.cloudapi;

import java.net.URL;
import java.time.Instant;

import lombok.Value;

@Value
public class RemarkableDownloadLink {

	URL url;
	Instant expirationTime;

}
