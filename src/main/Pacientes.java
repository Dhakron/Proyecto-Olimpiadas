package main;

import java.util.Scanner;
import java.util.StringTokenizer;

public class Pacientes {
	public static void main(String[] args) {
		MapaTriple datos = new MapaTriple();
		Boolean ejecutar = true;
		Scanner in = new Scanner(System.in);
		System.out.println(txtBienvenida);
		while (ejecutar) {
			System.out.println(txtMenu);
			String operacion = in.nextLine();
			switch (operacion) {
			case "1":
				altaRegistro(datos, in);
				break;
			case "2":
				bajaRegistro(datos, in);
				;
				break;
			case "3":
				consultarRegistros(datos, in);
				break;
			case "4":
				mostrarRegistros(datos, in);
				break;
			case "5":
				System.out.println(txtProgramaCerrado);
				ejecutar = false;
				break;
			default:
				System.out.println(txtOpcionNoValida);
				System.out.println(txtContinuar);
				operacion = in.nextLine();
			}
		}
	}

	private static void altaRegistro(MapaTriple datos, Scanner in) {
		Boolean alta = true;
		while (alta) {
			System.out.println(txtAlta);
			String operacion = in.nextLine();
			switch (operacion) {
			case "0":
				System.out.println(txtOperacionCancelada);
				alta = false;
				break;
			default:
				try {
					StringTokenizer st = new StringTokenizer(operacion, "/");
					if (st.countTokens() == 3) {
						String dni = st.nextToken().toUpperCase();
						String segSocial = st.nextToken();
						String nombre = st.nextToken();
						if (!validarNombre(nombre)) {
							System.out.println(txtNombreNoValido);
						} else if (!validarDNI(dni)) {
							System.out.println(txtDNINoValido);
						} else if (!validarSegSocial(segSocial)) {
							System.out.println(txtSegSocialNoValido);
						} else {
							datos.inserta(dni, Integer.parseInt(segSocial), nombre);
							System.out.println(txtOperacionFinalizada);
						}
					} else {
						System.out.println(txtDatosNoValidos);
					}
				} catch (YaExiste yaExiste) {
					System.out.println(yaExiste.getMessage());
				} catch (Exception e) {
					System.out.println(txtDatosNoValidos);
				}
				System.out.println(txtContinuar);
				operacion = in.nextLine();
				break;
			}
		}
	}

	private static void bajaRegistro(MapaTriple datos, Scanner in) {
		Boolean baja = true;
		while (baja) {
			System.out.println(txtBaja);
			String operacion = in.nextLine();
			switch (operacion) {
			case "0":
				System.out.println(txtOperacionCancelada);
				baja = false;
				break;
			default:
				try {
					StringTokenizer st = new StringTokenizer(operacion, "/");
					if (st.countTokens() == 2) {
						String dni = st.nextToken().toUpperCase();
						String segSocial = st.nextToken();
						if(!validarDNI(dni)) {
							System.out.println(txtDNINoValido);
						}else if(!validarSegSocial(segSocial)) {
							System.out.println(txtSegSocialNoValido);
						}else {
							datos.elimina(dni, Long.parseLong(segSocial));
							System.out.println(txtOperacionFinalizada);
						}
						
					} else {
						System.out.println(txtDatosNoValidos);
					}
				} catch (NoExiste noExiste) {
					System.out.println(noExiste.getMessage());
				} catch (Exception e) {
					System.out.println(txtDatosNoValidos);
				}
				System.out.println(txtContinuar);
				operacion = in.nextLine();
				break;
			}
		}
	}

	private static void consultarRegistros(MapaTriple datos, Scanner in) {
		Boolean consulta = true;
		while (consulta) {
			System.out.println(txtConsultarDatos);
			String operacion = in.nextLine();
			switch (operacion) {
			case "0":
				System.out.println(txtOperacionCancelada);
				consulta = false;
				break;
			default:
				try {
					StringTokenizer st = new StringTokenizer(operacion, "/");
					if (st.countTokens() == 2) {
						switch (st.nextToken()) {
						case "1":
							String dni = st.nextToken().toUpperCase();
							if(!validarDNI(dni)) {
								System.out.println(txtDNINoValido);
							}else {
								System.out.println("NSegSocial: " + datos.segsocial(dni));
							}
							break;
						case "2":
							String segSocial = st.nextToken();
							if(!validarSegSocial(segSocial)) {
								System.out.println(txtSegSocialNoValido);
							}else {
								System.out.println("DNI: " + datos.dni(Long.parseLong(segSocial)));
							}
							break;
						case "3":
							String nombre = st.nextToken();
							if(!validarNombre(nombre)) {
								System.out.println(txtNombreNoValido);
							}else {
								System.out.println("DNI/DNIs: \n" + datos.dni(nombre));
							}
							break;
						default:
							System.out.println(txtDatosNoValidos);
							break;
						}
						System.out.println(txtOperacionFinalizada);
					} else {
						System.out.println(txtDatosNoValidos);
					}
				} catch (NoExiste noExiste) {
					System.out.println(noExiste.getMessage());
				} catch (Exception e) {
					System.out.println(txtDatosNoValidos);
				}
				System.out.println(txtContinuar);
				operacion = in.nextLine();
				break;
			}
		}
	}

	private static void mostrarRegistros(MapaTriple datos, Scanner in) {
		System.out.println(txtListaPacientes);
		datos.muestra();
		System.out.println(txtContinuar);
		in.nextLine();
	}

	private static Boolean validarNombre(String nombre) {
		if (nombre.length() < 1 || nombre.startsWith(" ") || nombre.endsWith(" ")) {
			return false;
		} else {
			for (int i = 0; i < nombre.length(); i++) {
				if (!Character.isLetter(nombre.charAt(i))) {
					if (nombre.charAt(i) == ' ' && nombre.charAt(i - 1) != ' ') {
						continue;
					} else {
						return false;
					}
				}
			}
			return true;
		}
	}

	private static Boolean validarSegSocial(String segSocial) {
		for (int i = 0; i < segSocial.length(); i++) {
			if (!Character.isDigit(segSocial.charAt(i))) {
				return false;
			}
		}
		if (Long.parseLong(segSocial) < 0 || String.valueOf(segSocial).length() != 12) {
			return false;
		} else {
			int nProvincia = Integer.parseInt(segSocial.substring(0, 2));
			int nAfiliado = Integer.parseInt(segSocial.substring(2, 10));
			int codControl = Integer.parseInt(segSocial.substring(10));
			long npYna = Long.parseLong(segSocial.substring(0, 10));
			if ((nProvincia > 0 && nProvincia < 54) || nProvincia == 66) {
				if (nAfiliado < 10000000) {
					if (codControl == (nAfiliado + nProvincia * 10000000) % 97) {
						return true;
					}
				} else {
					if (codControl == npYna % 97) {
						return true;
					}
				}
			}
			return false;
		}
	}

	public static Boolean validarDNI(String dni) {
		char[] letras = { 'T', 'R', 'W', 'A', 'G', 'M', 'Y', 'F', 'P', 'D', 'X', 'B', 'N', 'J', 'Z', 'S', 'Q', 'V', 'H',
				'L', 'C', 'K', 'E' };
		if (dni.startsWith("X")) {
			dni = "0" + dni.substring(1);
		} else if (dni.startsWith("Y")) {
			dni = "1" + dni.substring(1);
		} else if (dni.startsWith("Z")) {
			dni = "2" + dni.substring(1);
		}
		if (dni.length() != 9 || dni.contains(" ")) {
			return false;
		} else {
			for (int i = 0; i < dni.length(); i++) {
				if (Character.isLetter(dni.charAt(i)) && i != 8) {
					return false;
				}
			}
			if (letras[Integer.parseInt(dni.substring(0, 8)) % 23] == dni.charAt(8)) {
				return true;
			} else {
				return false;
			}
		}
	}

	static private String txtBienvenida = "-------------------BIENVENIDO-------------------";
	static private String txtMenu = "------------------------------------------------\r\n"
			+ "----------Que operacion desea realizar----------\r\n"
			+ "------------------------------------------------\r\n"
			+ "1 = Dar de alta a un nuevo paciente.            \r\n"
			+ "2 = Dar de baja a un paciente.                  \r\n"
			+ "3 = Consultar datos de un paciente o pacientes. \r\n"
			+ "4 = Mostrar todos los pacientes dados de alta.  \r\n"
			+ "5 = Salir.                                      \r\n"
			+ "------------------------------------------------";
	static private String txtAlta = "------------------------------------------------\r\n"
			+ "-------Dar de alta a un nuevo paciente:---------\r\n"
			+ "------------------------------------------------\r\n" 
			+ "Introduczca los datos de la siguente forma:\r\n"
			+ "DNI/NSegSocial/Nombre\r\n"
			+ "0 = Volver al menu principal.\r\n"
			+ "------------------------------------------------\r"
			+ "Recuerde que el DNI y el NSegSocial deben ser validos.\r\n"
			+ "------------------------------------------------";
	static private String txtBaja = "------------------------------------------------\r\n"
			+ "----------Dar de baja a un paciente:------------\r\n"
			+ "------------------------------------------------\r\n" 
			+ "Introduczca los datos de la siguente forma:\r\n"
			+ "DNI/NSegSocial\r\n"
			+ "0 = Volver al menu principal.\r\n"
			+ "------------------------------------------------\r"
			+ "Recuerde que el DNI y el NSegSocial deben ser validos.\r\n"
			+ "------------------------------------------------";
	static private String txtDatosNoValidos = "---------------Datos no validos-----------------";
	static private String txtOperacionCancelada = "-------------Operacion cancelada----------------";
	static private String txtProgramaCerrado = "---------------Programa cerrado-----------------";
	static private String txtOpcionNoValida = "------------------------------------------------\r\n"
			+ "El valor introducido no coincide con ninguna\r\n" + "opcion, recuerde que los operaciones validas\r\n"
			+ "son 1,2,3,4 o 5.\r\n" + "------------------------------------------------";
	static private String txtOperacionFinalizada = "-------------Operacion finalizada---------------";
	static private String txtConsultarDatos = "------------------------------------------------\r\n"
			+ "--Consultar datos de un paciente o pacientes:---\r\n"
			+ "------------------------------------------------\r\n"
			+ "1/DNI = Obtener el N Seg.Social a partir del DNI\r\n"
			+ "2/NSegSocial = Obtener DNI a partir del N SegSocial\r\n"
			+ "3/Nombre = Obtener DNI/DNIs a partir del nombre\r\n" 
			+ "0 = Volver al menu principal.\r\n"
			+ "------------------------------------------------\r"
			+ "Recuerde que el DNI y el NSegSocial deben ser validos.\r\n"
			+ "------------------------------------------------";
	static private String txtListaPacientes = "--------------Lista de pacientes:---------------";
	static private String txtContinuar = "Presione enter para continuar...";
	static private String txtNombreNoValido = "---------------Nombre no valido-----------------";
	static private String txtDNINoValido = "-----------------DNI no valido------------------";
	static private String txtSegSocialNoValido = "-------------N Seg. Social no valido------------";
}
