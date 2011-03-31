/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
*/
package fitlibrary.specify;

import fitlibrary.ArrayFixture;

public class EntityInNestedArray extends ArrayFixture {
	protected Entity first = new Entity("first");
	protected Entity second = new Entity("second");
	
	public EntityInNestedArray() {
		setActualCollection(new EntityElement[] { new EntityElement() } );
	}
	public Entity findEntity(String s) {
		if (s.equals("first"))
			return first;
		return second;
	}
	public String showEntity(Entity e) {
		return e.toString();
	}
	public class EntityElement {
		private EntitySubElement entitySubElement = new EntitySubElement();

		public int getId() {
			return 1;
		}
		public EntitySubElement getSub() {
			return entitySubElement;
		}
		public ActionEntity[] getActions() {
			ActionEntity[] actionEntities = {
					new ActionEntity(), new ActionEntity()
			};
			return actionEntities;
		}
	}
	public class EntitySubElement {
		private ActionEntity[] actionEntities = {
				new ActionEntity(), new ActionEntity()
		};
		public ActionEntity[] getActions() {
			return actionEntities;
		}
	}
	public class ActionEntity {
		public Entity getType() {
			return first;
		}
		public String getName() {
			return "First";
		}
	}
	public static class Entity {
		private String s;

		public Entity(String s) {
			this.s = s;
		}
		@Override
		public String toString() {
			return s;
		}
	}
}
