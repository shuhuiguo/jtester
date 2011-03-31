package fitlibrary.specify.plugin;

import java.util.List;

import fitlibrary.object.DomainFixtured;
import fitlibrary.traverse.DomainAdapter;

public class HasNewInstancePlugin implements DomainAdapter, DomainFixtured {
	private Person person;
	private Person[] persons;
	private List<Person> personList;

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public void setPersons(Person[] persons) {
		this.persons = persons;
	}

	public Person[] getPersons() {
		return persons;
	}

	public List<Person> getPersonList() {
		return personList;
	}

	public void setPersonList(List<Person> personList) {
		this.personList = personList;
	}

	public Person newInstancePlugin(Class<?> type) {
		if (type == Person.class)
			return new ConcretePerson();
		if (type == Crash.class)
			throw new RuntimeException("bang");
		return null;
	}

	public void setDog(Dog dog) {
		//
	}

	public void setCrash(Crash crash) {
		//
	}

	public interface Person {
		//
	}

	public static class ConcretePerson implements Person {
		private String name;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}

	public interface Dog {
		//
	}

	public interface Crash {
		//
	}

	// @Override
	public Object getSystemUnderTest() {
		return null;
	}
}
