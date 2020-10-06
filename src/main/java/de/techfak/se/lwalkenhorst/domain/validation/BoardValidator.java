package de.techfak.se.lwalkenhorst.domain.validation;

import de.techfak.se.lwalkenhorst.domain.Position;

import java.util.List;

public interface BoardValidator {

    boolean validate(final List<Position> positions);
}
