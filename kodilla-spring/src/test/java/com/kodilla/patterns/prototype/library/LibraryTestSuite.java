package com.kodilla.patterns.prototype.library;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class LibraryTestSuite {

    @Test
    void testGetBooks() throws CloneNotSupportedException {
        // Given
        Library library = new Library("City Library 1");
        Book book1 = new Book("Secrets of Java", "Ian Bull", LocalDate.of(2010, 1, 1));
        Book book2 = new Book("Design Patterns", "Gang of Four", LocalDate.of(1994, 5, 20));
        Book book3 = new Book("Spring in Action", "Craig Walls", LocalDate.of(2018, 10, 5));

        library.getBooks().add(book1);
        library.getBooks().add(book2);
        library.getBooks().add(book3);

        Library clonedLibrary = library.shallowCopy();
        clonedLibrary.setName("City Library 2 (Shallow)");

        Library deepClonedLibrary = library.deepCopy();
        deepClonedLibrary.setName("City Library 3 (Deep)");

        // When
        library.getBooks().remove(book1);

        // Then
        System.out.println(library);
        System.out.println(clonedLibrary);
        System.out.println(deepClonedLibrary);

        assertEquals(2, library.getBooks().size());
        assertEquals(2, clonedLibrary.getBooks().size());
        assertEquals(3, deepClonedLibrary.getBooks().size());

        assertSame(clonedLibrary.getBooks(), library.getBooks());
        assertNotSame(deepClonedLibrary.getBooks(), library.getBooks());
    }
}
