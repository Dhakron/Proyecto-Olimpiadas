package main;

import java.util.HashMap;

public class MapaTriple {
	private HashMap<String, String> nombreByDni;
	private HashMap<String, Integer> numSegSocialByDni;
	private HashMap<Integer, String> nombreByNumSegSocial;

	public MapaTriple() {
		super();
		this.nombreByDni = new HashMap<String, String>();
		this.numSegSocialByDni = new HashMap<String, Integer>();
		this.nombreByNumSegSocial = new HashMap<Integer, String>();
	}

	public void inserta(String dni, int segSocial, String nombre) throws YaExiste {
		if (this.nombreByDni.containsKey(dni) || numSegSocialByDni.containsKey(dni)
				|| nombreByNumSegSocial.containsKey(segSocial)) {
			throw new YaExiste("Error: El DNI o el N Seg.Social ya estan registrados");
		} else {
			this.nombreByDni.put(dni, nombre);
			this.numSegSocialByDni.put(dni, segSocial);
			this.nombreByNumSegSocial.put(segSocial, nombre);
		}
	}

	public void elimina(String dni, long segSocial) throws NoExiste {
		if (this.nombreByDni.containsKey(dni) && numSegSocialByDni.containsKey(dni)
				&& nombreByNumSegSocial.containsKey(segSocial)) {
			this.nombreByDni.remove(dni);
			this.numSegSocialByDni.remove(dni);
			this.nombreByNumSegSocial.remove(segSocial);
		} else {
			throw new NoExiste("Error: No existe ningun registro con este DNI o N Seg.Social");
		}
	}

	public Integer segsocial(String dni) throws NoExiste {
		try {
			return this.numSegSocialByDni.get(dni);
		} catch (Exception e) {
			throw new NoExiste("Error: No existe ningun registro relacionado con este DNI");
		}
	}

	public String dni(long segSocial) throws NoExiste {
		try {
			for (String key : this.numSegSocialByDni.keySet()) {
				if (this.numSegSocialByDni.get(key) == segSocial) {
					return key;
				}
			}
			throw new NoExiste("Error: No existe ningun registro relacionado con este Nº Seg.Social");
		} catch (Exception e) {
			throw new NoExiste("Error: No existe ningun registro relacionado con este Nº Seg.Social");
		}
	}

	public String dni(String nombre) throws NoExiste {
		try {
			String result = "";
			for (String key : this.nombreByDni.keySet()) {
				if (this.nombreByDni.get(key).equals(nombre)) {
					if (!result.equals("")) {
						result += ", ";
					}
					result += key;
				}
			}
			if (!result.equals("")) {
				return result;
			} else {
				throw new NoExiste("Error: No existe ningun registro relacionado con este nombre");
			}
		} catch (Exception e) {
			throw new NoExiste("Error: No existe ningun registro relacionado con este nombre");
		}
	}

	public void muestra() {
		if (this.nombreByDni.keySet().size() == 0) {
			System.out.println("No hay registros...");
		} else {
			for (String key : this.nombreByDni.keySet()) {
				String nombre = this.nombreByDni.get(key);
				long segSocial = this.numSegSocialByDni.get(key);
				String dni = key;
				System.out.println("DNI:" + dni + "\tSEG.SOCIAL:" + segSocial + "\tNOMBRE:" + nombre);
			}
		}
	}
}
