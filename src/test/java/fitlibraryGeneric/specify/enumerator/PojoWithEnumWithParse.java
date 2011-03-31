package fitlibraryGeneric.specify.enumerator;

public class PojoWithEnumWithParse {
	public enum ColorType {
		Red {
			@Override
			public String speak() {
				return "my name is red";
			}
		},
		Blue {
			@Override
			public String speak() {
				return "my name is blue";
			}
		},
		Green {
			@Override
			public String speak() {
				return "my name is green";
			}
		};

		abstract String speak();

		public static ColorType parse(String type) {
			for (ColorType ct : ColorType.values()) {
				if (ct.name().equals(type)) {
					return ct;
				}
			}

			throw new IllegalArgumentException("Cannot parse Color from : " + type);
		}
	}

	public String process(ColorType type) {
		return type.speak();
	}
}
