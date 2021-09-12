package br.com.starter.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import br.com.starter.admin.dto.JobDTO;
import br.com.starter.admin.entity.Job;
import br.com.starter.admin.repository.JobRepository;
import br.com.starter.config.DTO;
import br.com.starter.config.ScheduledTasks;
import br.com.starter.config.ServiceDTO;
import br.com.starter.config.query.GenericSpesification;
import br.com.starter.config.query.Request;

@Service
public class JobService implements ServiceDTO<JobDTO> {

	@Autowired
	private JobRepository repository;

	@Autowired
	private ScheduledTasks scheduledTasks;

	@Override
	public Page<DTO> findAll(Request request) {
		return repository.findAll(new GenericSpesification<>(request.getList()), request.getPageable())
				.map(p -> new JobDTO(p));
	}

	@Override
	public DTO save(JobDTO dto) {
		return new JobDTO(repository.save(new Job(dto)));
	}

	@Override
	public DTO findById(long id) {
		return new JobDTO(repository.findById(id).orElse(null));
	}

	@Override
	public void delete(long id) {
		repository.deleteById(id);
	}

	public void agendar(Long id) {
		Job job = repository.findById(id).get();
		stop(job.getId());
		scheduledTasks.scheduleTask(job);
		job.setAtivo(true);
		repository.save(job);
	}

	public void agendarJobs() {
		repository.findByAtivoTrue().forEach(job -> {
			scheduledTasks.scheduleTask(job);
		});
	}

	public void incluirJobs() {
//		if (repository.findByName("BUSCA MATERIAL") == null)
//			repository.save(new Job("BUSCA MATERIAL", true, 6, UnidadeTempo.HORAS, LocalDateTime.now()));

	}

	public void start(long id) {
		Job job = repository.findById(id).orElse(null);
		switch (job.getName()) {
		default:
			// INCLUIR METODO PARA EXECUÇÃO
			break;
		}
		job.setAtivo(true);
		repository.save(job);
	}

	public void stop(long id) {
		Job job = repository.findById(id).orElse(null);
		ScheduledTasks.stop(job);
		job.setAtivo(false);
		repository.save(job);
	}

	public DTO configurar(JobDTO dto) {
		Job job = repository.findById(dto.getId()).get();

		job.setTempo(dto.getTempo());
		job.setUnidadeTempo(dto.getUnidadeTempo());

		repository.save(job);

		if (job.getAtivo())
			agendar(job.getId());
		else
			stop(job.getId());

		return new JobDTO(job);
	}

}
