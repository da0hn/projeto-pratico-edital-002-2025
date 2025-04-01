package br.com.ghonda.core.service;

import br.com.ghonda.core.dto.NewUsuarioPayload;
import br.com.ghonda.core.dto.UpdateUsuarioPayload;
import br.com.ghonda.core.dto.UsuarioDetailPayload;
import br.com.ghonda.core.domain.Usuario;
import br.com.ghonda.core.exceptions.ResourceNotFoundException;
import br.com.ghonda.core.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UsuarioDetailPayload registrar(final NewUsuarioPayload request) {
        final var usuario = Usuario.builder()
            .nome(request.nome())
            .email(request.email())
            .senha(this.passwordEncoder.encode(request.password()))
            .role(request.role())
            .build();

        return UsuarioDetailPayload.of(this.usuarioRepository.save(usuario));
    }

    @Transactional(readOnly = true)
    public Page<UsuarioDetailPayload> findAll(final Pageable pageable) {
        return this.usuarioRepository.findAll(pageable)
            .map(UsuarioDetailPayload::of);
    }

    @Transactional(readOnly = true)
    public UsuarioDetailPayload findById(final Long id) {
        return this.usuarioRepository.findById(id)
            .map(UsuarioDetailPayload::of)
            .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
    }

    @Transactional
    public UsuarioDetailPayload update(final Long id, final UpdateUsuarioPayload payload) {
        final var usuario = this.usuarioRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        usuario.setNome(payload.nome());
        usuario.setEmail(payload.email());
        if (payload.password() != null && !payload.password().isBlank()) {
            usuario.setSenha(this.passwordEncoder.encode(payload.password()));
        }
        usuario.setRole(payload.role());

        return UsuarioDetailPayload.of(this.usuarioRepository.save(usuario));
    }

    @Transactional
    public void deleteById(final Long id) {
        if (!this.usuarioRepository.existsById(id)) {
            throw new ResourceNotFoundException("Usuário não encontrado");
        }
        this.usuarioRepository.deleteById(id);
    }

}
