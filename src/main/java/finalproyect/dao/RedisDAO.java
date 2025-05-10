package finalproyect.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPooled;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

public class RedisDAO implements GenericDAO {

    private final JedisPooled jedis;
    private final ObjectMapper mapper;

    public RedisDAO() {
        try {
            this.mapper = new ObjectMapper();
            this.mapper.findAndRegisterModules();
            Properties config = new Properties();
            InputStream input = getClass().getClassLoader().getResourceAsStream("redis.properties");

            if (input == null) {
                throw new RuntimeException("No se encontró redis.properties en resources");
            }

            config.load(input);
            String host = config.getProperty("redis.host", "localhost");
            int port = Integer.parseInt(config.getProperty("redis.port", "6379"));

            this.jedis = new JedisPooled(host, port);

        } catch (Exception e) {
            throw new RuntimeException("Error al cargar configuración de Redis", e);
        }
    }

    private <T> String clave(Class<T> tipo, Integer id) {
        return tipo.getSimpleName().toLowerCase() + ":" + id;
    }

    @Override
    public <T> T buscarPorId(Class<T> tipo, Integer id) {
        String key = clave(tipo, id);
        String json = jedis.get(key);
        if (json == null) {
            return null;
        }
        try {
            System.out.println("Resultado recuperado desde REDIS");
            return mapper.readValue(json, tipo);
        } catch (Exception e) {
            throw new RuntimeException("Error al deserializar desde Redis", e);
        }
    }

    @Override
    public <T> List<T> buscarTodos(Class<T> tipo) {
        Set<String> keys = jedis.keys(tipo.getSimpleName().toLowerCase() + ":*");
        List<T> resultado = new ArrayList<>();
        boolean datosEnRedis = false;
        for (String key : keys) {
            String json = jedis.get(key);
            if (json != null) {
                datosEnRedis = true;
                try {
                    T obj = mapper.readValue(json, tipo);
                    resultado.add(obj);
                } catch (Exception e) {
                    System.err.println("No se pudo deserializar clave: " + key);
                }
            }
        }
        if (datosEnRedis) {
            System.out.println("Resultados recuperados desde REDIS");
        }
        return resultado;
    }

    @Override
    public <T> T guardar(T entidad) {
        try {
            Class<?> clazz = entidad.getClass();
            Integer id = (Integer) clazz.getMethod("getId").invoke(entidad); // requiere método getId()
            String key = clave(clazz, id);
            String json = mapper.writeValueAsString(entidad);
            jedis.set(key, json);
            System.out.println("Operación de guardado ejecutada en REDIS");
            return entidad;
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar en Redis", e);
        }
    }

    @Override
    public <T> T actualizar(T entidad) {
        System.out.println("Operación de actualización ejecutada en REDIS");
        return guardar(entidad); // en Redis, guardar y actualizar es lo mismo
    }

    @Override
    public <T> void eliminar(T entidad) {
        try {
            Class<?> clazz = entidad.getClass();
            Integer id = (Integer) clazz.getMethod("getId").invoke(entidad);
            jedis.del(clave(clazz, id));
            System.out.println("Operación de borrado ejecutada en REDIS");
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar en Redis", e);
        }
    }
}
