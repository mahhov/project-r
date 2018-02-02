#version 330 core

uniform mat4 view;

in vec3 mVertexPosition;
in vec3 mVertexNormal;
in vec3 vertexColor;

out vec3 fragColor;

const float ambientFactor = .2, diffusePower = 1.2, specularPower = 15;
const vec3 lightPosition = vec3(128, 250, -128);
const vec3 cameraForward = vec3(0, 0, 1);

float calcDiffuseFactor(vec3 lightDirection) {
    return max(dot(mVertexNormal, lightDirection), 0);
}

float calcSpecularFactor(vec3 lightDirection) {
    vec3 reflected = reflect(-lightDirection, mVertexNormal); // mVertexNormal * 2 * dot(mVertexNormal, lightDirection) - lightDirection
    vec3 vReflected = (view * vec4(reflected, 0)).xyz;
    float factor = max(dot(vReflected, cameraForward), 0);
    return pow(factor, specularPower);
}

void main() {
    vec3 lightDirection = normalize(lightPosition - mVertexPosition);
    float diffuseFactor = calcDiffuseFactor(lightDirection);
    float specularFactor = calcSpecularFactor(lightDirection);
    float brightness = min(ambientFactor + diffuseFactor * diffusePower + specularFactor, 1);
    // brightness = floor(brightness * 5) / 5;
    fragColor = vertexColor * brightness;
}