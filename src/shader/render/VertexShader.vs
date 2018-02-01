#version 330 core

uniform mat4 projection, view;

layout (location=0) in vec3 position;
layout (location=1) in mat4 model;
layout (location=6) in vec3 vertexNormal;
layout (location=5) in vec3 color;

out vec3 mVertexPosition;
out vec3 mVertexNormal;
out vec3 vertexColor;

void main() {
    vec4 mPosition = model * vec4(position, 1);
    mVertexPosition = mPosition.xyz;
    mVertexNormal = (model * vec4(vertexNormal, 0)).xyz;
    vertexColor = color;

    gl_Position = projection * view * mPosition;
}