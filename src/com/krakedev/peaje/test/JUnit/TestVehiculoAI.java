package com.krakedev.peaje.test.JUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.krakedev.peaje.entidades.Conductor;
import com.krakedev.peaje.entidades.TagElectronico;
import com.krakedev.peaje.entidades.Vehiculo;
import com.krakedev.peaje.servicios.EstacionPeaje;
import com.krakedev.peaje.util.ImpresorUtil;

public class TestVehiculoAI {

	// 1, 2, 3 y 4: Crear conductor, vehiculo, tag y asociarlos por composicion
	@Test
	public void testCrearYAsociarObjetosPorComposicion() {
		Conductor conductor = new Conductor("2026081001", "Ana", "Perez");
		Vehiculo vehiculo = new Vehiculo("PBX-1234");
		TagElectronico tag = new TagElectronico("TAG-001");

		vehiculo.setPropietario(conductor);
		vehiculo.setTag(tag);

		assertNotNull(vehiculo.getPropietario());
		assertNotNull(vehiculo.getTag());
		assertEquals(conductor, vehiculo.getPropietario());
		assertEquals(tag, vehiculo.getTag());
	}

	// 5: Ejecutar imprimir() propio del vehiculo
	@Test
	public void testImprimirVehiculo() {
		Vehiculo vehiculo = new Vehiculo("PBX-5678");
		vehiculo.imprimir();

		assertEquals("PBX-5678", vehiculo.getPlaca());
		assertEquals("L", vehiculo.getTipo());
	}

	// 6: Ejecutar ImpresorUtil.imprimirVehiculo()
	@Test
	public void testImpresorUtilImprimirVehiculo() {
		Conductor conductor = new Conductor("2026081002", "Luis", "Gomez");
		Vehiculo vehiculo = new Vehiculo("PBX-9012");
		TagElectronico tag = new TagElectronico("TAG-002");

		vehiculo.setPropietario(conductor);
		vehiculo.setTag(tag);

		ImpresorUtil.imprimirVehiculo(vehiculo);

		assertNotNull(vehiculo.getPropietario());
		assertNotNull(vehiculo.getTag());
	}

	// 7: Validar metodos de negocio - registrarVehiculo
	@Test
	public void testRegistrarVehiculoTipoValido() {
		EstacionPeaje estacion = new EstacionPeaje();
		Conductor conductor = new Conductor("2026081003", "Marta", "Diaz");

		Vehiculo vehiculo = estacion.registrarVehiculo("PBX-1111", "L", conductor, "TAG-003");

		assertNotNull(vehiculo);
		assertEquals("L", vehiculo.getTipo());
	}

	@Test
	public void testRegistrarVehiculoTipoInvalido() {
		EstacionPeaje estacion = new EstacionPeaje();
		Conductor conductor = new Conductor("2026081004", "Pedro", "Lopez");

		Vehiculo vehiculo = estacion.registrarVehiculo("PBX-2222", "Z", conductor, "TAG-004");

		assertNull(vehiculo);
	}

	// 7: Validar metodos de negocio - recargarTag
	@Test
	public void testRecargarTagValido() {
		EstacionPeaje estacion = new EstacionPeaje();
		TagElectronico tag = new TagElectronico("TAG-005");

		boolean resultado = estacion.recargarTag(tag, 20.0);

		assertTrue(resultado);
		assertEquals(20.0, tag.getSaldo(), 0.0001);
	}

	@Test
	public void testRecargarTagInvalido() {
		EstacionPeaje estacion = new EstacionPeaje();
		TagElectronico tag = new TagElectronico("TAG-006");

		boolean resultado = estacion.recargarTag(tag, -5.0);

		assertTrue(!resultado);
		assertEquals(0, tag.getSaldo(), 0.0001);
	}

	// 7: Validar metodos de negocio - cobrarPeaje
	@Test
	public void testCobrarPeajeConSaldoSuficiente() {
		EstacionPeaje estacion = new EstacionPeaje();
		Conductor conductor = new Conductor("2026081005", "Vero", "Nunez");
		Vehiculo vehiculo = estacion.registrarVehiculo("PBX-3333", "L", conductor, "TAG-007");
		estacion.recargarTag(vehiculo.getTag(), 10.0);

		boolean resultado = estacion.cobrarPeaje(vehiculo);

		assertTrue(resultado);
		assertEquals(9.0, vehiculo.getTag().getSaldo(), 0.0001);
	}

	@Test
	public void testCobrarPeajeSinSaldoSuficiente() {
		EstacionPeaje estacion = new EstacionPeaje();
		Conductor conductor = new Conductor("2026081006", "Nico", "Paredes");
		Vehiculo vehiculo = estacion.registrarVehiculo("PBX-4444", "P", conductor, "TAG-008");

		boolean resultado = estacion.cobrarPeaje(vehiculo);

		assertTrue(!resultado);
		assertEquals(0, vehiculo.getTag().getSaldo(), 0.0001);
	}

	// 7: Validar metodos de negocio - transferirSaldoTag
	@Test
	public void testTransferirSaldoTagValida() {
		EstacionPeaje estacion = new EstacionPeaje();
		TagElectronico origen = new TagElectronico("TAG-009");
		TagElectronico destino = new TagElectronico("TAG-010");
		estacion.recargarTag(origen, 15.0);

		boolean resultado = estacion.transferirSaldoTag(origen, destino, 5.0);

		assertTrue(resultado);
		assertEquals(10.0, origen.getSaldo(), 0.0001);
		assertEquals(5.0, destino.getSaldo(), 0.0001);
	}

	@Test
	public void testTransferirSaldoTagInvalidaPorSaldoInsuficiente() {
		EstacionPeaje estacion = new EstacionPeaje();
		TagElectronico origen = new TagElectronico("TAG-011");
		TagElectronico destino = new TagElectronico("TAG-012");
		estacion.recargarTag(origen, 10.0);

		boolean resultado = estacion.transferirSaldoTag(origen, destino, 999.0);

		assertTrue(!resultado);
		assertEquals(10.0, origen.getSaldo(), 0.0001);
		assertEquals(0, destino.getSaldo(), 0.0001);
	}
}

//Prompt mejorado: Para los métodos de negocio, como cada uno devuelve boolean o null en vez de lanzar excepción, necesito probar dos caminos por método: un caso donde la validación pasa (ejemplo: monto positivo, tipo correcto, saldo suficiente) y un caso donde la validación falla (monto negativo, tipo inválido, saldo insuficiente). Así compruebo que el if/else de cada método realmente funciona en ambas direcciones, no solo en el camino feliz.
//Para el caso inválido de cobrarPeaje, necesito un vehículo con tag en saldo 0.0 recién creado — no puedo reutilizar el tag que ya recargué antes, porque tendría saldo de sobra y el caso no fallaría
