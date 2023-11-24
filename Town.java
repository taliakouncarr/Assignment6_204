import java.util.Objects;
/**
 * @author Talia
 */
public class Town implements Comparable<Town>{

	private String name;
	
	
	public Town(String name) {
		this.name = name;
	}
	public Town(Town tempTown) {
		this.name = tempTown.getName();
	}
	

	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		return this.name.hashCode();
	}


	
	@Override
	public int compareTo(Town t) {
		return this.name.compareTo(t.getName());
	}	
	
	@Override
		public boolean equals(Object OTown) {
		Town T = (Town) OTown;
			if(this.name.equals(T.getName())) {
				return true;
			} else {
				return false;
			}
		}
	
	@Override
	public String toString() {
		return name;
	}
	
	
}