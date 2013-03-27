package de.bockstallmann.interaktive.vorlesung.interfaces;

import de.bockstallmann.interaktive.vorlesung.support.CollectionObservable;

public interface CollectionObserverInterface {
	void update(String command, CollectionObservable collectionObservable);
}
