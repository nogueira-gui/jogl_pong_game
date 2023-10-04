package utils;

public class TransformationUtils {
    public static void aplicarTransformacoes(float dx, float dy, float dz, float rot_x, float rot_y, float rot_z, float[][] vertices) {
        // Aplicar rotações
        float cosY = (float) Math.cos(Math.toRadians(rot_y));
        float sinY = (float) Math.sin(Math.toRadians(rot_y));
        float cosX = (float) Math.cos(Math.toRadians(rot_x));
        float sinX = (float) Math.sin(Math.toRadians(rot_x));
        float cosZ = (float) Math.cos(Math.toRadians(rot_z));
        float sinZ = (float) Math.sin(Math.toRadians(rot_z));

        for (int i = 0; i < vertices.length; i++) {
            float[] vertex = vertices[i];
            float x = vertex[0];
            float y = vertex[1];
            float z = vertex[2];

            // Rotação em torno do eixo Y
            float new_x = x * cosY - z * sinY;
            float new_z = x * sinY + z * cosY;
            x = new_x;
            z = new_z;

            // Rotação em torno do eixo X
            float new_y = y * cosX - z * sinX;
            z = y * sinX + z * cosX;
            y = new_y;

            // Rotação em torno do eixo Z
            new_x = x * cosZ - y * sinZ;
            float new_y2 = x * sinZ + y * cosZ;
            x = new_x;
            y = new_y2;

            // Atualiza as coordenadas do vértice após as rotações
            // soma dx dy e dz para translação
            vertex[0] = x + dx; //atualiza eixo x e soma a translação com dx
            vertex[1] = y + dy; //atualiza eixo y e soma a translação com dy
            vertex[2] = z + dz; //atualiza eixo z e soma a translação com dz
        }
    }
}
