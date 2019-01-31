package it.mltk.poc.axonpms.delivery.query;

import lombok.Value;

import java.util.UUID;

@Value
public class GetOneProjectQuery {
    UUID uuid;
}
