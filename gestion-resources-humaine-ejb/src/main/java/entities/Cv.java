package entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Cv
 *
 */
@Entity

public class Cv implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String FilePath;
	private float FileSize;
	private String FileType;
	private String OriginalFileType;
	private String SenderIp;
	
	@OneToMany(mappedBy="cv1",cascade={CascadeType.ALL})
	private List<Experience> expriences;
	@OneToMany(mappedBy="cv2",cascade={CascadeType.ALL})
	private List<Formation> Formations;
	@OneToMany(mappedBy="cv3",cascade={CascadeType.ALL})
	private List<Hobies> Hobies;
	@OneToMany(mappedBy="cv4",cascade={CascadeType.ALL})
	private List<language> languages;
	@OneToMany(mappedBy="cv5",cascade={CascadeType.ALL})
	private List<Project> Projects;
	@OneToMany(mappedBy="cv6",cascade={CascadeType.ALL})
	private List<Skills> skills;
	
	@ManyToOne
	private Candidate Candidates;
	
	
	public Candidate getCandidates() {
		return Candidates;
	}

	public void setCandidates(Candidate candidates) {
		Candidates = candidates;
	}



	public List<Experience> getExpriences() {
		return expriences;
	}



	public void setExpriences(List<Experience> expriences) {
		this.expriences = expriences;
	}



	public List<Formation> getFormations() {
		return Formations;
	}



	public void setFormations(List<Formation> formations) {
		Formations = formations;
	}



	public List<Hobies> getHobies() {
		return Hobies;
	}



	public void setHobies(List<Hobies> hobies) {
		Hobies = hobies;
	}


	public List<language> getLanguages() {
		return languages;
	}



	public void setLanguages(List<language> languages) {
		this.languages = languages;
	}



	public List<Project> getProjects() {
		return Projects;
	}



	public void setProjects(List<Project> projects) {
		Projects = projects;
	}


	public List<Skills> getSkills() {
		return skills;
	}



	public void setSkills(List<Skills> skills) {
		this.skills = skills;
	}




	

	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getFilePath() {
		return FilePath;
	}



	public void setFilePath(String filePath) {
		FilePath = filePath;
	}



	public float getFileSize() {
		return FileSize;
	}



	public void setFileSize(float fileSize) {
		FileSize = fileSize;
	}



	public String getFileType() {
		return FileType;
	}



	public void setFileType(String fileType) {
		FileType = fileType;
	}



	public String getOriginalFileType() {
		return OriginalFileType;
	}



	public void setOriginalFileType(String originalFileType) {
		OriginalFileType = originalFileType;
	}



	public String getSenderIp() {
		return SenderIp;
	}



	public void setSenderIp(String senderIp) {
		SenderIp = senderIp;
	}

	@Override
	public String toString() {
		return "Cv [id=" + id + ", FilePath=" + FilePath + ", FileSize=" + FileSize + ", FileType=" + FileType
				+ ", OriginalFileType=" + OriginalFileType + ", SenderIp=" + SenderIp + "]";
	}
	
	

	public Cv(int id, String filePath, float fileSize, String fileType, String originalFileType, String senderIp) {
		super();
		this.id = id;
		FilePath = filePath;
		FileSize = fileSize;
		FileType = fileType;
		OriginalFileType = originalFileType;
		SenderIp = senderIp;
	}

	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((SenderIp == null) ? 0 : SenderIp.hashCode());
		result = prime * result + id;
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cv other = (Cv) obj;
		if (SenderIp == null) {
			if (other.SenderIp != null)
				return false;
		} else if (!SenderIp.equals(other.SenderIp))
			return false;
		if (id != other.id)
			return false;
		return true;
	}



	public Cv() {
		super();
	}
	
	
   
}
