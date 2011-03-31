package fitlibraryGeneric.specify.genericFinder;

public class Pair<S, T> {
	private S first;
	private T second;

	public Pair(S i, T j) {
		this.first = i;
		this.second = j;
	}

	public void setFirst(S first) {
		this.first = first;
	}

	public void setSecond(T second) {
		this.second = second;
	}

	@Override
	public String toString() {
		return "Pair[" + first + "," + second + "]";
	}
}
