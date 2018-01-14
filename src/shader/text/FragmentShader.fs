#version 330 core

in vec2 fTextureCoordinate;
in vec4 fColor;

out vec4 fragColor;

uniform sampler2D textureSampler;

void main() {
    fragColor = texture(textureSampler, fTextureCoordinate) * fColor;
}