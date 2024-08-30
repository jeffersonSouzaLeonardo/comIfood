package br.com.lett.comifood.record;

import java.time.LocalDateTime;

public record EventRecord(String id, String code, String fullCode, String orderId, String merchantId, LocalDateTime createdAt) {}
