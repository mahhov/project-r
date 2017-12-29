#version 330 core

uniform mat4 projection, view;

layout (location=0) in vec3 position;
layout (location=1) in mat4 model;
layout (location=5) in vec3 color;
layout (location=6) in vec3 vertexNormal;

out vec3 vertexColor;
out vec3 mvVertexPosition;
out vec3 mvVertexNormal;

void main() {
    vec4 mvPosition = view * model * vec4(position, 1.0);
    mvVertexPosition = mvPosition.xyz;
    mvVertexNormal = normalize(view * model * vec4(vertexNormal, 0.0)).xyz;

    gl_Position = projection * mvPosition;
    vertexColor = color;
}