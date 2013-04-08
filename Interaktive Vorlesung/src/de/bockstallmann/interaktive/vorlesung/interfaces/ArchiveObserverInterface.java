package de.bockstallmann.interaktive.vorlesung.interfaces;

import de.bockstallmann.interaktive.vorlesung.support.ArchiveObservable;

public interface ArchiveObserverInterface {
	void update(String command, ArchiveObservable archiveObservalbe);
}
