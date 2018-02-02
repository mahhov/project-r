#version 330 core

uniform mat4 projection, view;

layout (location=0) in vec3 position;
layout (location=1) in mat4 model;
layout (location=6) in vec3 normal;
layout (location=5) in vec3 color;

out vec3 vertexColor;
const float ambientFactor = .2, diffusePower = .6, specularPower = 15;
const vec3[] lightDirection = vec3[] (normalize(vec3(2, 5, -1)), normalize(vec3(-1, 5, 2)));
const vec4[] lightPosition = vec4[] (vec4(1600, 150, 0, 100)); // last element is light intensity
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

float calcBrightnessDirection(vec3 mVertexPosition, vec3 mVertexNormal, vec3 lightDirection) {
    float diffuseFactor = calcDiffuseFactor(mVertexNormal, lightDirection);
    float specularFactor = calcSpecularFactor(mVertexNormal, lightDirection);
    return diffuseFactor * diffusePower + specularFactor;
}

float calcBrightnessPosition(vec3 mVertexPosition, vec3 mVertexNormal, vec3 lightPosition, float lightIntensity) {
    vec3 lightDirection = lightPosition - mVertexPosition;
    float lightDistance = length(lightPosition - mVertexPosition);
    return calcBrightnessDirection(mVertexPosition, mVertexNormal, lightDirection / lightDistance) / lightDistance * lightIntensity;
}

void main() {
    vec4 mPosition = model * vec4(position, 1);
    vec3 mVertexPosition = mPosition.xyz;
    vec3 mVertexNormal = (model * vec4(normal, 0)).xyz;

    gl_Position = projection * view * mPosition;
    
    float brightness = ambientFactor;
    brightness += calcBrightnessDirection(mVertexPosition, mVertexNormal, lightDirection[0]);
    brightness += calcBrightnessDirection(mVertexPosition, mVertexNormal, lightDirection[1]);
    // brightness += calcBrightnessPosition(mVertexPosition, mVertexNormal, lightPosition[0].xyz, lightPosition[0].w);
    
    vertexColor = color * min(brightness, 1);
}