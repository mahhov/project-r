#version 330 core

in vec3 vertexColor;
in vec3 mVertexPosition;
in vec3 mVertexNormal;

out vec3 fragColor;

const float ambientFactor = .2;
const vec3 lightPosition = vec3(128, 64, 128);

float calcDiffuseFactor(vec3 lightDirection, vec3 normal) {
    return max(dot(normal, lightDirection), 0.0);
}

void main() {
    vec3 lightDirection = normalize(lightPosition - mVertexPosition);
    float diffuseFactor = calcDiffuseFactor(lightDirection, mVertexNormal);
    fragColor = vertexColor * min(diffuseFactor + ambientFactor, 1);
}