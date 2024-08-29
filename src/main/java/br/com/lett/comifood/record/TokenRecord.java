package br.com.lett.comifood.record;

public record TokenRecord(String accessToken, String type, Integer expiresIn) {}
