#version 330 core

uniform mat4 view;

in vec3 mVertexPosition;
in vec3 mVertexNormal;
in vec3 vertexColor;

out vec3 fragColor;

const float ambientFactor = .2, diffusePower = .8;
const vec3 lightPosition = vec3(128, 250, -128);
const vec3 cameraForward = vec3(0, 0, 1);
const float specularPower = 10;

float calcDiffuseFactor(vec3 lightDirection) {
    return max(dot(mVertexNormal, lightDirection), 0.0);
}

float calcSpecularFactor(vec3 lightDirection) {
    vec3 reflected = normalize(reflect(-lightDirection, mVertexNormal));
    vec3 vReflected = (view * vec4(reflected, 0.0)).xyz;
    float factor = max(dot(vReflected, cameraForward), 0.0);
    return pow(factor, specularPower);
}

void main() {
    vec3 lightDirection = normalize(lightPosition - mVertexPosition);
    float diffuseFactor = calcDiffuseFactor(lightDirection);
    float specularFactor = calcSpecularFactor(lightDirection);
    float brightness = min(specularFactor + diffuseFactor * diffusePower + ambientFactor, 1);
    // brightness = floor(brightness * 5) / 5;
    fragColor = vertexColor * brightness;
}