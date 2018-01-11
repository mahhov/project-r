#version 330 core

layout (location=0) in vec2 position;
layout (location=1) in vec2 textureCoordinate;

out vec2 fTextureCoordinate;

void main() {
    gl_Position = vec4(position, -1, 1);
    fTextureCoordinate = textureCoordinate;
}