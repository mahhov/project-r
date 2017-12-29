#version 330 core

in vec3 vertexColor;
in vec3 mvVertexPosition;
in vec3 mvVertexNormal;

out vec3 fragColor;

float calcPointLight(vec3 lightPosition, vec3 position, vec3 normal) {
    vec3 lightDirection = lightPosition - position;
    vec3 lightDirectionNormalized  = normalize(lightDirection);
    float diffuseFactor = max(dot(normal, lightDirectionNormalized), 0.0);
    return diffuseFactor;
}

void main() {
    float diffuseFactor = calcPointLight(vec3(0, 30, 0), mvVertexPosition, mvVertexNormal);
    fragColor = vertexColor * diffuseFactor;
}