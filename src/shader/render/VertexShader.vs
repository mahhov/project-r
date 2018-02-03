#version 330 core

const int LIGHTING_FADE_DISTANCE = 200, LIGHTING_MAX_DISTANCE = LIGHTING_FADE_DISTANCE + 150;
const float DISTANT_LIGHT = .7;
const float ambientFactor = .2, diffusePower = .6, specularPower = 15;
const vec3[] lightDirection = vec3[] (normalize(vec3(2, 5, -1)), normalize(vec3(-1, 5, 2)));
const vec4[] lightPosition = vec4[] (vec4(1600, 150, 0, 100)); // last element is light intensity
const vec3 cameraForward = vec3(0, 0, 1);

uniform mat4 projection, view;
uniform vec3 viewPosition;
uniform bool antialias;

layout (location=0) in vec3 position;
layout (location=1) in mat4 model;
layout (location=6) in vec3 normal;
layout (location=5) in vec4 color;

out vec4 vertexColor;

float calcDiffuseFactor(vec3 mVertexNormal, vec3 lightDirection) {
    return max(dot(mVertexNormal, lightDirection), 0);
}

float calcSpecularFactor(vec3 mVertexNormal, vec3 lightDirection) {
    vec3 reflected = reflect(-lightDirection, mVertexNormal); // mVertexNormal * 2 * dot(mVertexNormal, lightDirection) - lightDirection
    vec3 vReflected = (view * vec4(reflected, 0)).xyz;
    float factor = max(dot(vReflected, cameraForward), 0);
    return pow(factor, specularPower);
}

float calcLightDirection(vec3 mVertexNormal, vec3 lightDirection, bool specular) {
    float diffuseFactor = calcDiffuseFactor(mVertexNormal, lightDirection);
    float specularFactor = specular ? calcSpecularFactor(mVertexNormal, lightDirection) : 0;
    return diffuseFactor * diffusePower + specularFactor;
}

float calcLightPosition(vec3 mVertexPosition, vec3 mVertexNormal, vec3 lightPosition, float lightIntensity, bool specular) {
    vec3 lightDirection = lightPosition - mVertexPosition;
    float lightDistance = length(lightPosition - mVertexPosition);
    return calcLightDirection(mVertexNormal, lightDirection / lightDistance, specular) / lightDistance * lightIntensity;
}

float calcLightBrightness(vec3 mVertexPosition, vec3 mVertexNormal, bool specular) {
    float brightness = ambientFactor;
    brightness += calcLightDirection(mVertexNormal, lightDirection[0], specular);
    brightness += calcLightDirection(mVertexNormal, lightDirection[1], specular);
    // brightness += calcLightPosition(mVertexPosition, mVertexNormal, lightPosition[0].xyz, lightPosition[0].w, specular);
    return brightness;
}

float calcDistantBrightness(vec3 mVertexPosition) {
    return calcLightBrightness(mVertexPosition, normalize(viewPosition - mVertexPosition), false);
}

float calcBrightness(float distance, vec3 mVertexPosition, vec3 mVertexNormal) {
    if (!antialias || distance < LIGHTING_FADE_DISTANCE)
        return min(calcLightBrightness(mVertexPosition, mVertexNormal, true), 1);
   
    else if (distance < LIGHTING_MAX_DISTANCE) {
        float brightness = min(calcLightBrightness(mVertexPosition, mVertexNormal, true), 1);
        return mix(brightness, calcDistantBrightness(mVertexPosition), (distance - LIGHTING_FADE_DISTANCE) / (LIGHTING_MAX_DISTANCE - LIGHTING_FADE_DISTANCE));    
    
    } else
        return calcDistantBrightness(mVertexPosition);
}

void main() {
    vec4 mPosition = model * vec4(position, 1);
    vec4 vmPosition = view * mPosition;
    vec3 mVertexPosition = mPosition.xyz;
    vec3 mVertexNormal = (model * vec4(normal, 0)).xyz;

    gl_Position = projection * vmPosition;
    
    float distance = length(vmPosition.xyz);
    vertexColor = vec4(color.rgb * calcBrightness(distance, mVertexPosition, mVertexNormal), color.a);
}