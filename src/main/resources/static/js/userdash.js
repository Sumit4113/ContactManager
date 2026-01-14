

// DOM elements
const menuToggle = document.getElementById('menuToggle');
const sidebar = document.getElementById('sidebar');
const mainContent = document.getElementById('mainContent');
const overlay = document.getElementById('overlay');
const navItems = document.querySelectorAll('.nav-item[data-section]');
const sections = document.querySelectorAll('.section');
const addContactForm = document.getElementById('addContactForm');
const contactList = document.getElementById('contactList');

// Toggle sidebar
function toggleSidebar() {
	sidebar.classList.toggle('active');
	overlay.classList.toggle('active');

	if (window.innerWidth > 768) {
		mainContent.classList.toggle('sidebar-open');
	}
}

// Close sidebar
function closeSidebar() {
	sidebar.classList.remove('active');
	overlay.classList.remove('active');

	if (window.innerWidth > 768) {
		mainContent.classList.remove('sidebar-open');
	}
}





// Add contact
function addContact(contactData) {
	const newContact = {
		id: Date.now(),
		...contactData
	};

	contacts.push(newContact);
	renderContacts();

	// Show success message
	alert('Contact added successfully!');
}

// Edit contact
function editContact(id) {
	const contact = contacts.find(c => c.id === id);
	if (contact) {
		// For demo purposes, just show an alert
		alert(`Editing ${contact.firstName} ${contact.lastName}`);
	}
}

// Delete contact
function deleteContact(id) {
	if (confirm('Are you sure you want to delete this contact?')) {
		contacts = contacts.filter(c => c.id !== id);
		renderContacts();
	}
}

// Call contact
function callContact(phone) {
	alert(`Calling ${phone}...`);
}

// Event listeners
menuToggle.addEventListener('click', toggleSidebar);
overlay.addEventListener('click', closeSidebar);

// Navigation
navItems.forEach(item => {
	item.addEventListener('click', (e) => {
		e.preventDefault();
		const sectionId = item.getAttribute('data-section');
		showSection(sectionId);
	});
});



// Handle window resize
window.addEventListener('resize', () => {
	if (window.innerWidth > 768) {
		overlay.classList.remove('active');
	} else {
		mainContent.classList.remove('sidebar-open');
	}
});

// Initialize
renderContacts();

// Auto-open sidebar on desktop
if (window.innerWidth > 768) {
	sidebar.classList.add('active');
	mainContent.classList.add('sidebar-open');
}
