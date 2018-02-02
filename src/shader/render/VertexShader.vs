#version 330 core

uniform mat4 projection, view;

layout (location=0) in vec3 position;
layout (location=1) in mat4 model;
layout (location=6) in vec3 normal;
layout (location=5) in vec3 color;

out vec3 vertexColor;

const float ambientFactor = .2, diffusePower = 1.2, specularPower = 15;
const vec3 lightPosition = vec3(1000, 2500, -500);
const vec3 cameraForward = vec3(0, 0, 1);

float calcDiffuseFactor(vec3 mVertexNormal, vec3 lightDirection) {
    return max(dot(mVertexNormal, lightDirection), 0);
}

float calcSpecularFactor(vec3 mVertexNormal, vec3 lightDirection) {
    vec3 reflected = reflect(-lightDirection, mVertexNormal); // mVertexNormal * 2 * dot(mVertexNormal, lightDirection) - lightDirection
    vec3 vReflected = (view * vec4(reflected, 0)).xyz;
    float factor = max(dot(vReflected, cameraForward), 0);
    return pow(factor, specularPower);
}

void main() {
    vec4 mPosition = model * vec4(position, 1);
    vec3 mVertexPosition = mPosition.xyz;
    vec3 mVertexNormal = (model * vec4(normal, 0)).xyz;

    gl_Position = projection * view * mPosition;
    
    vec3 lightDirection = normalize(lightPosition - mVertexPosition);
    float diffuseFactor = calcDiffuseFactor(mVertexNormal, lightDirection);
    float specularFactor = calcSpecularFactor(mVertexNormal, lightDirection);
    float brightness = min(ambientFactor + diffuseFactor * diffusePower + specularFactor, 1);
    vertexColor = color * brightness;
}